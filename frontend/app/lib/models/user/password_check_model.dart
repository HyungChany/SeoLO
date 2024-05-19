class PasswordCheckModel {
  final String nowPwd;

  PasswordCheckModel({
    required this.nowPwd,
  });

  Map<String, dynamic> toJson() => {
        'now_password': nowPwd,
      };
}
