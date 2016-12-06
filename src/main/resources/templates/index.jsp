﻿<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta content="ja" http-equiv="Content-Language" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>中水野澤のページへようこそ</title>
<style type="text/css">
.auto-style1 {
	text-align: center;
}

.auto-style2 {
	text-align: left;
	margin-left: 80px;
}

.auto-style3 {
	text-align: left;
	margin-left: 40px;
}

.auto-style5 {
	margin-left: 40px;
}

.auto-style6 {
	text-align: right;
	margin-left: 40px;
}

.auto-style7 {
	text-align: left;
	margin-left: 40px;
}
</style>
</head>

<body>
	<p class="auto-style1">中水野澤のページへようこそ</p>
	<p class="auto-style2">
		ここは中水青森中央市場 冷凍部 <br /> 野澤のwebページです。
	</p>
	<p class="auto-style2">ここから私の担当している商品の問い合わせや注文ができます。</p>
		<p class="auto-style2" th:text="${today}">Weekly Month dateの表示</p>
	<p class="auto-style3" style="height: 24px">
		使用するにはユーザーネームを入力してログインをしてください <br /> <br /> ユーザーネーム

	</p>
	<br />
	<form:form commandName="LoginForm_com"></form:form>
	<form action="/" method="post">
		<p class="auto-style5">
			<input name="loginName" type="text" />
		</p>
		<p class="auto-style5">パスワード</p>
		<p class="auto-style5">
			<input name="loginPassword" type="password" />
			<p class="auto-style6">
				<div class="auto-style7">
					<input name="Button_login" type="submit" value="ログイン" />
				</div>
			</p>
		</p>
	</form>
	<p class="auto-style6">新規ユーザーの方はこちらから</p>

	<form action="/register" method="post">
		<p class="auto-style6">
			<input name="toRegister" type="submit" value="新規ユーザー登録" />
		</p>
	</form>
</body>

</html>