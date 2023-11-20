package com.verda.BE.chat.service;

import com.verda.BE.board.entity.UserPostEntity;
import com.verda.BE.board.repository.BoardRepository;
import com.verda.BE.chat.dto.requestDto.CreateChatRoomRequestDto;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromFmDto;
import com.verda.BE.chat.dto.responseDto.GetChatRoomsFromUserDto;
import com.verda.BE.chat.entity.ChatRoomEntity;
import com.verda.BE.chat.repository.ChatRoomRepository;
import com.verda.BE.chat.repository.ChatRoomInterface;
import com.verda.BE.chat.repository.MessageRepository;
import com.verda.BE.login.entity.FundEntity;
import com.verda.BE.login.entity.UserEntity;
import com.verda.BE.login.repository.FundRepository;
import com.verda.BE.login.repository.KakaoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
     * @param createChatRoom
     */
    public void createChatRoom(CreateChatRoomRequestDto createChatRoom) {
        UserPostEntity getPost = boardRepository.findById(createChatRoom.getPostId())
                                    .orElseThrow(() -> new RuntimeException());
        UserEntity getUser = kakaoRepository.findById(createChatRoom.getUserId())
                                    .orElseThrow(() -> new RuntimeException());
        FundEntity getFund = fundRepository.findById(createChatRoom.getFmId())
                                    .orElseThrow(() -> new RuntimeException());
        ChatRoomEntity chatRoomEntity=new ChatRoomEntity(getPost,getUser,getFund);
        chatRoomRepository.save(chatRoomEntity);
    }

    /**
     * 유저가 게시글을 누르면 나올 제안서들 나오도록 조회 service
     * 상대방, 가장 최신글, 상대 이미지
     * @param postId
     * @return
     */
    public GetChatRoomsFromUserDto getChatListToUser(long postId) {
        List<ChatRoomInterface> chatList = chatRoomRepository.getChatListBypostId(postId);
        GetChatRoomsFromUserDto getChatRoomsFromUserDto=new GetChatRoomsFromUserDto(chatList);
        return getChatRoomsFromUserDto;
    }

    public GetChatRoomsFromUserDto getChatListAtRoomView(long userId) {
        List<ChatRoomInterface> chatList = chatRoomRepository.getChatListByUserId(userId);
        GetChatRoomsFromUserDto getChatRoomsFromUserDto=new GetChatRoomsFromUserDto(chatList);
        return getChatRoomsFromUserDto;
    }

    public GetChatRoomsFromFmDto getChatListAtRoomViewFromFm(long fmId){
        List<ChatRoomInterface> chatList = chatRoomRepository.getChatListByFmId(fmId);
        GetChatRoomsFromFmDto getChatRoomsFromFmDto=new GetChatRoomsFromFmDto(chatList);
        return getChatRoomsFromFmDto;
    }

    /* 채팅방 입장 후 */
    public void getPreMessage(long roomId) {

    }

}
