class TaskTemplatesModel {
  final int taskTemplateId;
  final String taskTemplateName;
  final String taskPrecaution;

  TaskTemplatesModel(
      {required this.taskTemplateId,
      required this.taskTemplateName,
      required this.taskPrecaution});

  factory TaskTemplatesModel.fromJson(Map<String, dynamic> json) {
    return TaskTemplatesModel(
      taskTemplateId: json['task_template_id'],
      taskTemplateName: json['task_template_name'],
      taskPrecaution: json['task_precaution'],
    );
  }
}
