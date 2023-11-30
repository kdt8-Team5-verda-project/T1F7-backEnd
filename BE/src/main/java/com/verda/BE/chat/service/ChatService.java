package com.verda.BE.chat.service;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.board.repository.BoardRepository;
import com.verda.BE.chat.dto.requestDto.ChatMessageRequestDTO;
import com.verda.BE.chat.dto.requestDto.CreateChatRoomRequestDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsByPostIdFromUserDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromFmDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromUserDTO;
import com.verda.BE.chat.dto.responseDto.GetTargetNameDTO;
import com.verda.BE.chat.dto.responseDto.RecieveMessageResponseDTO;
import com.verda.BE.chat.entity.ChatRoomEntity;
import com.verda.BE.chat.entity.MessageEntity;
import com.verda.BE.chat.repository.ChatRoomRepository;
import com.verda.BE.chat.repository.ChatRoomInterface;
import com.verda.BE.chat.repository.MessageRepository;

import com.verda.BE.chat.repository.PreChatInterface;
import com.verda.BE.common.ErrorCode;
import com.verda.BE.exception.ApiException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.KakaoRepository;
import com.verda.BE.login.member.domain.UserEntity;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final BoardRepository boardRepository;
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;
    private final CacheManager cacheManager;

    /* 채팅방 입장 전 실행 */

    /**
     * 펀드매니저가 '제안서작성' 버튼 클릭시, 채팅방 생성 service.
     *
     * @param createChatRoom
     */
    public void createChatRoom(CreateChatRoomRequestDTO createChatRoom) {
        UserPostEntity getPost = boardRepository.findById(createChatRoom.getPostId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BOARD));
        UserEntity getUser = kakaoRepository.findById(createChatRoom.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_MEMBER));
        FundEntity getFund = fundRepository.findById(createChatRoom.getFmId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FUND));
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity(getPost, getUser, getFund);
        chatRoomRepository.save(chatRoomEntity);
    }

    /**
     * 유저가 게시글을 누르면 나올 제안서들 나오도록 조회 service 상대방, 가장 최신글, 상대 이미지
     *
     * @param postId
     * @return
     */
    public GetChatRoomsByPostIdFromUserDTO getChatListToUser(long postId) {
        System.out.println(postId);
        List<ChatRoomInterface> chatList = chatRoomRepository.getChatListByPostId(postId);
        GetChatRoomsByPostIdFromUserDTO getChatRoomsFromUserDto = new GetChatRoomsByPostIdFromUserDTO(chatList);
        return getChatRoomsFromUserDto;
    }

    public Slice<GetChatRoomsFromUserDTO> getChatListAtRoomView(long userId,Pageable pageable) {
        Slice<ChatRoomInterface> chatList = chatRoomRepository.getChatListByUserId(userId, pageable);
        return chatList.map(GetChatRoomsFromUserDTO::new);
    }

    public Slice<GetChatRoomsFromFmDTO> getChatListAtRoomViewFromFm(long fmId, Pageable pageable) {
        Slice<ChatRoomInterface> chatList = chatRoomRepository.getChatListByFmId(fmId, pageable);
        return chatList.map(GetChatRoomsFromFmDTO::new);
    }

    /* 채팅방 입장 후 */

    /**
     * 채팅방 예전 채팅기록 가져오기
     * @param roomId
     * @return
     */
    @Cacheable(cacheNames = "Message", key = "#roomId", condition = "#roomId != null")
    public List<PreChatInterface> getPreMessage(long roomId) {
        List<PreChatInterface> messageList = messageRepository.getPreChat(roomId);
//        GetPreChatListDTO getPreChatListDto = new GetPreChatListDTO(messageList);
        return messageList;
    }

    /**
     * 채팅전송
     * @param chatMessageRequestDTO
     */
    public RecieveMessageResponseDTO sendMessage(ChatMessageRequestDTO chatMessageRequestDTO) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository
                                            .findById(chatMessageRequestDTO.getRoomId())
                                            .orElseThrow(() -> new RuntimeException());

        MessageEntity message= new MessageEntity(chatMessageRequestDTO.getContent(), chatMessageRequestDTO.getSender(),chatRoomEntity);
        messageRepository.save(message);

        MessageEntity currentMessage = messageRepository.getCurrentMessageEntity();
        RecieveMessageResponseDTO recieve=new RecieveMessageResponseDTO(currentMessage.getContent(),currentMessage.getSenderEmail(),currentMessage.getSendTime());
        return recieve;
    }

    /**
     * 유저용 채팅방 이름 가져오기
     * @param roomId
     * @return
     */
    public GetTargetNameDTO getUserChatName(long roomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_CHAT));
        GetTargetNameDTO getTargetNameDTO=new GetTargetNameDTO(chatRoom.getFundEntity().getName());
        return getTargetNameDTO;
    }

    /**
     * 펀드매니저용 채팅방 이름 가져오기
     * @param roomId
     * @return
     */
    public GetTargetNameDTO getFmChatName(long roomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_CHAT));
        GetTargetNameDTO getTargetNameDTO =new GetTargetNameDTO(chatRoom.getUserEntity().getName());
        return getTargetNameDTO;
    }

    /**
     * 캐시데이터 가져오고 사용하기
     */
    public void addElementToCachedJsonArray(String cacheName,String key, Map<String, String> newElement ){
        try {
            // 캐시 가져오기
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null) {
                throw new Exception("Cache " + cacheName + " not found");
            }

            // key에 해당하는 JSON 배열 가져오기
            List<Map<String, String>> jsonArray = cache.get(key, ArrayList.class);

            // 새로운 요소 추가
            jsonArray.add(newElement);

            // 캐시에 업데이트된 JSON 배열 저장
            cache.put(key, jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 테스트용(메시지 전송)
     * @param requestDTO
     */
    public void saveMessage(ChatMessageRequestDTO requestDTO) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_CHAT));
        MessageEntity newMessage = new MessageEntity(requestDTO.getContent(),requestDTO.getSender(),chatRoom);
        messageRepository.save(newMessage);
        Map<String, String> newElement = new HashMap<>();
        newElement.put("sender_email",requestDTO.getSender());
        newElement.put("content",requestDTO.getContent());
        addElementToCachedJsonArray("Message", String.valueOf(requestDTO.getRoomId()),newElement);
    }
}
