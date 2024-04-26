class NewsModel {
  final String title;
  final String preview;
  final String author;
  final String date;
  final String? thum;
  final String link;

  NewsModel({
    required this.title,
    required this.preview,
    required this.author,
    required this.date,
    this.thum,
    required this.link,
  });

  factory NewsModel.fromJson(Map<String, dynamic> json) {
    return NewsModel(
      title: json['title'],
      preview: json['preview'],
      author: json['author'],
      date: json['date'],
      thum: json['thum'],
      link: json['link'],
    );
  }

  Map<String, dynamic> toJson() => {
    'title': title,
    'preview': preview,
    'author': author,
    'date': date,
    'thum': thum,
    'link': link,
  };
}

