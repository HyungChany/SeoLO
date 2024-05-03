class PasswordChangeModel {
  final String newPwd;
  final String checkNewPwd;

  PasswordChangeModel({
    required this.newPwd,
    required this.checkNewPwd,
  });

  Map<String, dynamic> toJson() => {
    'new_password': newPwd,
    'check_new_password': checkNewPwd,
  };
}

