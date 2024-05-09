class CoreIssueModel {
  final String lockerUid;
  final int machineId;
  final int taskTemplateId;
  final String taskPrecaution;
  final String endTime;
  final String facilityName;
  final String machineName;
  final String machineCode;
  final String manager;
  final String taskTemplateName;


  CoreIssueModel({
    required this.lockerUid,
    required this.machineId,
    required this.taskTemplateId,
    required this.taskPrecaution,
    required this.endTime,
    required this.facilityName,
    required this.machineName,
    required this.machineCode,
    required this.manager,
    required this.taskTemplateName,
  });

  Map<String, dynamic> toJson() => {
    'locker_uid': lockerUid,
    'machine_id': machineId,
    'task_template_id': taskTemplateId,
    'task_precaution': taskPrecaution,
    'end_time': endTime,
  };
}

