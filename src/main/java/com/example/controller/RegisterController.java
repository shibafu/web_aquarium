package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.UserMaster;
import com.example.form.RegisterForm;
import com.example.orders.GroupOrders;
import com.example.repository.UserMasterRepository;
import com.example.utils.StringUtils;


@Controller
@SessionAttributes(names="RegisterForm")
public class RegisterController {

	public final static String VALIDATION_ERROR = "妥当性エラー";
	public final static String REGISTER_SUCCESSED = "登録成功";


	//オリジナル文字操作ユーティリティを生成
	@Autowired
	StringUtils su;

	// データアクセスリポジトリ
	@Autowired
	UserMasterRepository userRepos;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model,
			@ModelAttribute("RegisterForm") RegisterForm x_RegisterForm) {

		model.addAttribute("RegisterForm",x_RegisterForm);
		return "user_register/register";
	}

	@RequestMapping(value = "/register_confirm", method = RequestMethod.POST)
	public String rehgister_confim(Model model,
			@Validated(GroupOrders.class) @ModelAttribute("RegisterForm") RegisterForm x_RegisterForm,
			BindingResult x_BindingResult) {
		String toRegister_page;
		String MockPassword = null;

		// 遷移先ページチェック
		toRegister_page = judge_to_page(register_judge(x_BindingResult));

		// ページ遷移に成功するなら、パスワード疑似文字列生成
		if (toRegister_page.equals("user_register/register_confirm")) {
			MockPassword = su.passwordset(x_RegisterForm.getCustomerPassword().length());
			model.addAttribute("mockpassword", MockPassword);
		}

		return toRegister_page;
	}

	@RequestMapping(value = "/register_complete", method = RequestMethod.POST)
	public String rehgister_complete(Model model, @ModelAttribute("RegisterForm") RegisterForm x_RegisterForm,
			@Validated(GroupOrders.class) BindingResult x_BindingResult) {

		// インサートのやり方が分からないでござる
		UserMaster x_cm = UserMaster_set(x_RegisterForm);

		userRepos.saveAndFlush(x_cm);

		return "user_register/register_complete";
	}

	// ログインチェックメソッド▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
	/*
	 * ログインページのエラーチェック
	 */
	private String register_judge(BindingResult x_result) {

		// バリデーションチェック
		if (x_result.hasErrors()) {
			return VALIDATION_ERROR;
		}
		return REGISTER_SUCCESSED;
	}

	/*
	 * 実際の遷移ページ作成
	 */
	private String judge_to_page(String error_mess) {
		if (error_mess.equals(VALIDATION_ERROR)) {
			return "user_register/register";
		}
		return "user_register/register_confirm";
	}

	/**
	 * レジスターフォームから、実体のエンティティを作成し、 セッターに入力する。 で、エンティティを返す
	 *
	 * @param レジスターフォーム
	 *            rf
	 */
	private UserMaster UserMaster_set(RegisterForm rf) {
		UserMaster um = new UserMaster();

		um.setUserLastname(rf.getLastName());
		um.setUserFirstname(rf.getFirstName());
		um.setAddress(rf.getCompanyAddress());
		um.setEmail(rf.getEMail());
		um.setAuthority("ROLE_USER");
		um.setEnabled(true);
//		um.setPhone(phone);
//		um.setHadlename(hadlename);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashGenerated = passwordEncoder.encode(rf.getCustomerPassword());

		um.setPassword(hashGenerated);

		return um;

	}

	//通常ユーザーセッション管理
	@ModelAttribute("RegisterForm")
	public RegisterForm setRegisterForm(RegisterForm x_r){
		return x_r;
	}


}
