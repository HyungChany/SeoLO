class MyTasksModel {
  final String facilityName;
  final String machineName;
  final String? startTime;
  final String? endTime;


  MyTasksModel({
    required this.facilityName,
    required this.machineName,
    this.startTime,
    this.endTime,
  });

  factory MyTasksModel.fromJson(Map<String, dynamic> json) {

    String startTimeString = json['taskStartTime'];
    List<String> startParts = startTimeString.split('T');
    String formattedStartTime =
        '${startParts[0]} | ${startParts[1].substring(0, 5)}';

    String endTimeString = json['taskEndTime'];
    List<String> endParts = endTimeString.split('T');
    String formattedEndTime =
        '${endParts[0]} | ${endParts[1].substring(0, 5)}';

    return MyTasksModel(
      facilityName: json['facilityName'],
      machineName: json['machineName'],
      startTime: formattedStartTime,
      endTime: formattedEndTime,
    );
  }
}

