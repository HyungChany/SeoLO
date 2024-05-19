class ChecklistModel {
  final int id;
  final String context;

  ChecklistModel({required this.id, required this.context});

  factory ChecklistModel.fromJson(Map<String, dynamic> json) {
    return ChecklistModel(
        id: json['id'] as int, context: json['context'] as String);
  }
}
