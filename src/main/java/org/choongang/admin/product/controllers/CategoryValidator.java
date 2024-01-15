package org.choongang.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component // Bean 등록 의존성
@RequiredArgsConstructor // Lombok에서 생성자 자동 생성
public class CategoryValidator implements Validator {

    private final CategoryRepository repository; // 카테고리 저장소 필드 생성자 자동 생성

    // Validator 인터페이스의 메서드 구현

    @Override
    public boolean supports(Class<?> clazz) {
        /* 클래스를 검증할 것인지 지정 메소드
        *  RequestCategory 클래스 검증 */
        return clazz.isAssignableFrom(RequestCategory.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 유효성 검사 메서드

        /* cateCd : 분류 코드 중복 여부 체크 */
        RequestCategory form = (RequestCategory)target;
        // RequestCategory(카테고리)선택 비교하기 위해 형변환
        String cateCd = form.getCateCd();
        // 분류 코드 가져와서 비교

        if (StringUtils.hasText(cateCd) && repository.existsById(cateCd)) {
            // 분류 코드가 비어있지 않고, 이미 존재하는 경우에는
            errors.rejectValue("cateCd", "Duplicated");
            // 에러를 발생 시킴
        }
    }
}
