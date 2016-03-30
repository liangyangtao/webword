<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- toTop:begin -->
<div class="backToTop">
		<a id="toTop" href="javascript:void(0);" title="回到顶部" hidefocus="true" style="opacity: 1; display: block;"><span class="bg-index s-ic-h top"></span>
		</a>
	</div>
<!-- toTop:end -->
<div name="footer" class="footer">
	<div class="foot1">
		友情链接：
		<p>银行联合信息网</p>
		|
		<p>北京百课萃管理咨询有限责任公司</p>
		|
		<p>银行金融终端</p>
		|
		<p>银行人</p>
		|
		<p>理财家园</p>
		|
		<p>银行邦</p>
		|
		<p>政经人</p>
	</div>
	<div class="foot2">
		客服电话：010-63368810（工作日9:30-17:30）
		<p>邮箱：ylxkf@unbank.info</p>
		地址：北京市西城区广安门外大街248号机械大厦1709
	</div>
	<div class="foot3">
		北京银联信投资顾问有限责任公司 版权所有 京ICP备 05058919号
		<p>Copyright 2009, All right reserved.</p>
	</div>
</div>
<div name="遮挡背景" class="ui_back2">
	<div name="遮挡背景" class="ui_back"></div>
</div>

<div class="box_content">
<div class=" pop_div2 dele_pop">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<p class='tips'>确认要删除？自定义栏目删除不可恢复！！</p>
			<div class="sb_button">
				<a onclick="del();" class="a1">确认</a>
				<a class="a2" onclick="closeDiv();">取消</a>
			</div>
		</div>
	<div class="pop_div2 exit_pop">
		<span class="pop_closeBt"></span>
		<h3>提示!</h3>
		<p class='tips'>确认退出?</p>
		<div class="sb_button">
			<a onclick="logout()" class="a1">确认</a><a class="a2"
				onclick="closeDiv()">取消</a>
		</div>
	</div>
	<div class="pop_div login_pop">
		<span class="pop_closeBt"></span>
		<div class="pop_logo">
			<img src="word/img/header1.png">
		</div>
		<form class="form-login" name="form-login" method="post" action=""
			autocomplete="off">
			<ul>
				<li><input type="text" name="username" style="display:none">
					<input type="text" name="username" id="username_1" maxlength="30"
					autocomplete="off"> <label for="username_1"
					class="placeholder">邮箱或手机号</label>
				</li>
				<li><input type="text" name="password" style="display:none">
					<input type="text" onfocus="this.type='password'" name="password"
					id="password_1" maxlength="30" autocomplete="off"
					onkeydown="javascript:butOnClick(event,'check_login()');">
					<label for="password_1" class="placeholder">密码<em>&nbsp;长度为6~30个字符</em>
				</label>
				</li>
				<li class="form_tips">
					<p>请输入您的账号</p> <a href="javascript:;" onClick="service_tel()">忘记密码？</a>
				</li>
				<li class="last"><input type="button" value="登录"
					class="btn-login curr" onClick="check_login()" tabindex="4" /> <input
					type="button" value="注册" class="btn-login"
					onClick="show_register()" tabindex="4" />
				</li>
			</ul>
		</form>
	</div>
	<div class="pop_div regist_pop">
		<span class="pop_closeBt"></span>
		<div class="pop_logo" style="padding:20px 0">
			<img src="word/img/header1.png">
		</div>
		<form class="form-login" name="form-login" method="post" action=""
			autocomplete="off">
			<ul>
				<li><input type="text" name="email" style="display:none">
					<input type="text" name="email" id="email_2" maxlength="30"
					autocomplete="off"> <label for="email_2"
					class="placeholder">邮箱</label>
					<p class="redTip">
						<em>*</em>必填
					</p>
				</li>
				<li><input type="text" name="phone" style="display:none">
					<input type="text" name="phone" id="phone_2" maxlength="30"
					autocomplete="off"> <label for="phone_2"
					class="placeholder">手机号</label>
				</li>
				<li><input type="text" name="phoneCheck" style="display:none">
					<input type="text" style="width:125px;" name="phoneCheck"
					id="phone_check" maxlength="10" autocomplete="off"> <input
					type="button" class="getTelYan" onclick="sendCheckMsg(this)"
					value="获取验证码"> <label for="phone_check" class="placeholder">手机验证码</label>
				</li>
				<li><input type="text" name="password" style="display:none">
					<input type="text" onfocus="this.type='password'" name="password"
					id="password_2" maxlength="30" autocomplete="off"
					onkeydown="javascript:butOnClick(event,'check_regist()');">
					<label for="password_2" class="placeholder">密码<em>&nbsp;长度为6~30个字符</em>
				</label>
					<p class="redTip">
						<em>*</em>必填
					</p>
				</li>
				<li class="form_tips">
					<p>请输入您的账号</p> <a style="right:56px;" href="javascript:;"
					onClick="forget_password()">忘记密码？</a>
				</li>
				<li class="last"><input type="button" value="登录"
					class="btn-login " onClick="show_login()" tabindex="4" /> <input
					type="button" value="注册" class="btn-login curr"
					onClick="check_regist()" tabindex="4" />
				</li>
			</ul>
		</form>
	</div>



	<div class="pop_div pass_back_1">
		<span class="pop_closeBt"></span>
		<div class="pop_logo">
			<img src="word/img/header1.png">
		</div>
		<form class="form-login" name="form-login" method="post" action="">
			<ul>
				<li><span style="width:102px">请输入登录邮箱</span> <input type="text"
					name="email" id="email_3" maxlength="30">
				</li>
				<li class="form_tips">
					<p>请输入您的账号</p>
				</li>
				<li class="last"><input type="button" value="下一步"
					onClick="get_pass_1()" class="btn-login curr" tabindex="4" />
				</li>
			</ul>
		</form>
	</div>
	<div class="pop_div pass_back_2">
		<span class="pop_closeBt"></span>
		<div class="pop_logo">
			<img src="word/img/header1.png">
		</div>
		<form class="form-login" name="form-login" method="post" action="">
			<ul>
				<li class="text">请拨打客服电话：010-63368810</li>
				<li class="last"><input type="button" value="确认"
					onClick="closeDiv();" class="btn-login curr" tabindex="4" />
				</li>
			</ul>
		</form>
	</div>
	<div class="pop_div pass_back_3">
		<span class="pop_closeBt"></span>
		<div class="pop_logo">
			<img src="word/img/header1.png">
		</div>
		<form class="form-login" name="form-login" method="post" action="">
			<ul>
				<li><span>原密码</span>
					<p class="blue">密码重置，请输入新密码确认</p>
				</li>
				<li><span>新密码</span> <input type="text" name="password"
					id="pass_new_4" maxlength="30">
					<p class="redTip">密码长度为6~30个字符</p>
				</li>
				<li><span>确认密码</span> <input type="text" name="password"
					id="pass_sure_4" maxlength="30">
					<p class="redTip">重复输入密码确认无误！</p>
				</li>
				<li><span>验证码</span> <input type="text" name="yanzheng"
					id="yanzheng_4" maxlength="16" tabindex="2" autocomplete="off"
					onkeydown="javascript:butOnClick(event);" style="width:88px">
					<img class="yan_img" src="word/img/yanzheng.png">
					<p class="blue">换张图片</p>
					<p class="redTip">请输入图片中的数字！</p>
				</li>
				<li class="form_tips">
					<p style="margin-left:76px;">请输入您的账号</p>
				</li>
				<li><input style="margin-left:230px" type="button" value="确定"
					onClick="get_pass_3()" class="btn-login curr" tabindex="4" />
				</li>
			</ul>
		</form>
	</div>
	<div class="pop_div pass_back_4">
		<span class="pop_closeBt"></span>
		<h3>密码修改成功！</h3>
		<a href="javascript:;" class="btn-login" onclick="closeDiv();">确认</a>
	</div>
</div>