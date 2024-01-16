package org.choongang.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardSaveService {

    private final BoardDataRepository boardDataRepository; // 의존성 주입
    private final FileUploadService fileUploadService; // 의존성 주입 파일 업로드
    private final MemberUtil;
    private final

        public BoardData save(RequestBoard form){
            String mode = form.getMode();
            mode = StringUtils.hasText(mode) ? mode : "write" ;
            Long seq = form.getSeq();

            BoardData data = null;
            if (seq !=null && mode.equals("update")) { //글 수정
                data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

            } else { // 글 작성
                data = new BoardData();
                data.setGid(form.getGid());
                data.setIp(request.getRemoteAddr());
                    data.setUa(request.getHeader("User-Agent"));
                        data.setMember(memberUtil.getMember());
            }

            return data;
    }
}
