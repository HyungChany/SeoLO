class MyInfoModel {
  final String employeeName;
  final String employeeTitle;
  final String employeeTeam;
  final String employeeThum;

  MyInfoModel({
    required this.employeeName,
    required this.employeeTitle,
    required this.employeeTeam,
    required this.employeeThum,
  });

  factory MyInfoModel.fromJson(Map<String, dynamic> json) {
    return MyInfoModel(
      employeeName: json['employeeName'],
      employeeTitle: json['employeeTitle'],
      employeeTeam: json['employeeTeam'],
      employeeThum: json['employeeThum'],
    );
  }

  Map<String, dynamic> toJson() => {
    'employeeName': employeeName,
    'employeeTitle': employeeTitle,
    'employeeTeam': employeeTeam,
    'employeeThum': employeeThum,
  };
}

