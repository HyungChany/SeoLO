class LoginModel {
  final String username;
  final String password;
  final String companyCode;

  LoginModel({
    required this.username,
    required this.password,
    required this.companyCode,
  });

  factory LoginModel.fromJson(Map<String, dynamic> json) {
    return LoginModel(
      username: json['username'],
      password: json['password'],
      companyCode: json['company_code'],
    );
  }

  Map<String, dynamic> toJson() => {
        'username': username,
        'password': password,
        'company_code': companyCode,
      };
}
