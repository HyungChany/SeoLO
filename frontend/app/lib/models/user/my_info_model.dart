class MyInfoModel {
  final String? employeeName;
  final String? employeeTitle;
  final String? employeeTeam;
  final String? employeeThum;
  final String? employeeNum;

  MyInfoModel({
    this.employeeName = '',
    this.employeeTitle = '',
    this.employeeTeam = '',
    this.employeeThum = '',
    this.employeeNum = '',
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

