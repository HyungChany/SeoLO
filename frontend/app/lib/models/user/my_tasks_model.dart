class MyTasksModel {
  final String? facilityName;
  final String? machineName;
  final String? startTime;
  final String? endTime;

  MyTasksModel({
    this.facilityName = '',
    this.machineName = '',
    this.startTime = '',
    this.endTime = '',
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
