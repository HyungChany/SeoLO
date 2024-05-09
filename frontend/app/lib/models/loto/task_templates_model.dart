class TaskTemplatesModel {
  final int taskTemplateId;
  final String taskTemplateName;
  final String taskTemplatePrecaution;

  TaskTemplatesModel(
      {required this.taskTemplateId,
      required this.taskTemplateName,
      required this.taskTemplatePrecaution});

  factory TaskTemplatesModel.fromJson(Map<String, dynamic> json) {
    return TaskTemplatesModel(
      taskTemplateId: json['task_template_id'],
      taskTemplateName: json['task_template_name'],
      taskTemplatePrecaution: json['task_template_precaution'],
    );
  }
}
