class MyInfoModel {
  final String employeeName;
  final String employeeTitle;
  final String employeeTeam;
  final String employeeThum;
  final String employeeNum;

  MyInfoModel({
    required this.employeeName,
    required this.employeeTitle,
    required this.employeeTeam,
    required this.employeeThum,
    required this.employeeNum,
  });

  factory MyInfoModel.fromJson(Map<String, dynamic> json) {
    return MyInfoModel(
      employeeName: json['employeeName'],
      employeeTitle: json['employeeTitle'],
      employeeTeam: json['employeeTeam'],
      employeeThum: json['employeeThum'],
      employeeNum: json['employeeNum'],
    );
  }
}

