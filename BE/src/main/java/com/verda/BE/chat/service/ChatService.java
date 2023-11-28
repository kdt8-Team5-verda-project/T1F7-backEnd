package com.verda.BE.chat.service;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.board.repository.BoardRepository;
import com.verda.BE.chat.dto.requestDto.ChatMessageRequestDTO;
import com.verda.BE.chat.dto.requestDto.CreateChatRoomRequestDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsByPostIdFromUserDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromFmDTO;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromUserDTO;
import com.verda.BE.chat.dto.responseDto.GetPreChatListDTO;
import com.verda.BE.chat.dto.responseDto.GetTargetName;
import com.verda.BE.chat.dto.responseDto.RecieveMessageResponseDTO;
import com.verda.BE.chat.entity.ChatRoomEntity;
import com.verda.BE.chat.entity.MessageEntity;
import com.verda.BE.chat.repository.ChatRoomRepository;
import com.verda.BE.chat.repository.ChatRoomInterface;
import com.verda.BE.chat.repository.MessageRepository;

import com.verda.BE.common.ErrorCode;
import com.verda.BE.exception.ApiException;
import jakarta.transaction.Transactional;
import java.util.List;

import com.verda.BE.login.member.domain.FundEntity;
import com.verda.BE.login.member.domain.FundRepository;
import com.verda.BE.login.member.domain.KakaoRepository;
import com.verda.BE.login.member.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final BoardRepository boardRepository;
    private final KakaoRepository kakaoRepository;
    private final FundRepository fundRepository;

    /* 채팅방 입장 전 실행 */

    /**
     * 펀드매니저가 '제안서작성' 버튼 클릭시, 채팅방 생성 service.
     *
     * @param createChatRoom
     */
    public void createChatRoom(CreateChatRoomRequestDTO createChatRoom) {
        UserPostEntity getPost = boardRepository.findById(createChatRoom.getPostId())
                .orElseThrow(() -> new RuntimeException());
        UserEntity getUser = kakaoRepository.findById(createChatRoom.getUserId())
                .orElseThrow(() -> new RuntimeException());
        FundEntity getFund = fundRepository.findById(createChatRoom.getFmId())
                .orElseThrow(() -> new RuntimeException());
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
     * 채팅방 예전 채팅기록 가져오기 sender추가하기
     *
     * @param roomId
     * @return
     */
    public GetPreChatListDTO getPreMessage(long roomId) {
        List<MessageEntity> messageList = messageRepository.findByChatRoomEntityId(roomId);
        GetPreChatListDTO getPreChatListDto = new GetPreChatListDTO(messageList);
        return getPreChatListDto;
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
     */
    public GetTargetName getUserChatName(long roomId) {
        String targetName = chatRoomRepository.getUserChatName(roomId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET));
        GetTargetName getTargetName=new GetTargetName(targetName);
        return getTargetName;
    }


    public GetTargetName getFmChatName(long roomId) {
        String targetName = chatRoomRepository.getFmChatName(roomId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET));
        GetTargetName getTargetName=new GetTargetName(targetName);
        return getTargetName;
    }
}
