class checklistmodel{
  final int id;
  final String context;
  checklistmodel({
    required this.id,
    required this.context
});
  factory checklistmodel.fromJson(Map<String, dynamic>json){
    return checklistmodel(
      id: json['id'] as int,
      context: json['context'] as String
    );
  }
}