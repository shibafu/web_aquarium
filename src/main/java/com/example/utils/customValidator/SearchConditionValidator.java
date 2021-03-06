package com.example.utils.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.form.newspapermanage.NewsSearchForm;

public class SearchConditionValidator implements ConstraintValidator<SearchConditionValid,NewsSearchForm> {

	private SearchConditionValid scv;
	@Override
	public void initialize(SearchConditionValid constraintAnnotation) {
		// TODO 自動生成されたメソッド・スタブ
		this.scv = constraintAnnotation;

	}
	@Override
	public boolean isValid(NewsSearchForm value, ConstraintValidatorContext context) {
		// TODO 自動生成されたメソッド・スタブ

		if(value.getArticleHeader() == null){
			return true;
		}

		/**
		 * 全て存在しないならFalse判定
		 */
		if(value.getArticleHeader().isEmpty()
				&& value.getArticleSentence().isEmpty()
				&& value.getCompanyName().isEmpty()
				&& value.getDateFrom() == null
				&& value.getDateTo() == null){
			return false;
		}
		return true;
	}

}
