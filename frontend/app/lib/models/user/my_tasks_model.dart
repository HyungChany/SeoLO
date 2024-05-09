class MyTasksModel {
  final String facilityName;
  final String machineName;
  final String startTime;
  final String endTime;


  MyTasksModel({
    required this.facilityName,
    required this.machineName,
    required this.startTime,
    required this.endTime,
  });

  factory MyTasksModel.fromJson(Map<String, dynamic> json) {
    return MyTasksModel(
      facilityName: json['facilityName'],
      machineName: json['machineName'],
      startTime: json['taskStartTime'],
      endTime: json['taskEndTime'],
    );
  }
}

