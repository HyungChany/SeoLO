import 'package:app/main.dart';
import 'package:app/view_models/main/news_view_model.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MainNewsBanner extends StatefulWidget {
  const MainNewsBanner({super.key});

  @override
  _MainNewsBannerState createState() => _MainNewsBannerState();
}

class _MainNewsBannerState extends State<MainNewsBanner> {
  final CarouselController _controller = CarouselController();

  @override
  Widget build(BuildContext context) {
    return Container(
        height: MediaQuery.of(context).size.height * 0.18,
        decoration: BoxDecoration(
            color: gray200,
            borderRadius: BorderRadius.circular(10.0),
            boxShadow: const [shadow]),
        child: sliderWidget());
  }

  Widget sliderWidget() {
    return CarouselSlider(
      carouselController: _controller,
      items: [0,1,2,3,4].map(
        (index) {
          return Builder(
            builder: (context) {
              final viewModel = Provider.of<NewsViewModel>(context);
              return Padding(
                padding: const EdgeInsets.all(8.0),
                child: SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: viewModel.news.length == 0
                        ? Image.asset('assets/images/loading_icon.png')
                        : Column(
                            children: [
                              Container(
                                  width: MediaQuery.of(context).size.width,
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      Flexible(
                                          child: RichText(
                                        overflow: TextOverflow.ellipsis,
                                        maxLines: 1,
                                        text: TextSpan(
                                            text: viewModel.news[index].title,
                                            style: TextStyle(
                                                fontWeight: FontWeight.bold,
                                                color: Colors.black,
                                                fontSize: 18)),
                                      ))
                                    ],
                                  )),
                              SizedBox(
                                height: 10,
                              ),
                              Row(
                                children: [
                                  Column(
                                    children: [
                                      SizedBox(
                                        height: 10,
                                      ),
                                      Container(
                                          width: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.6,
                                          child: Row(
                                            mainAxisAlignment:
                                                MainAxisAlignment.start,
                                            children: [
                                              Flexible(
                                                  child: RichText(
                                                overflow: TextOverflow.ellipsis,
                                                maxLines: 3,
                                                text: TextSpan(
                                                    text: viewModel
                                                        .news[index].preview,
                                                    style: TextStyle(
                                                        color: Colors.black)),
                                              ))
                                            ],
                                          )),
                                      SizedBox(
                                        height: 10,
                                      ),
                                      Row(
                                        children: [
                                          Text(viewModel.news[index].author),
                                          Text(' · '),
                                          Text(viewModel.news[index].date)
                                        ],
                                      )
                                    ],
                                  ),
                                  Image.network(
                                    viewModel.news[index].thum!,
                                    width: 85,
                                    height: 100,
                                  )
                                ],
                              ),
                            ],
                          )),
              );
            },
          );
        },
      ).toList(),
      options: CarouselOptions(
        height: 300,
        viewportFraction: 1.0,
        autoPlay: true,
        autoPlayInterval: const Duration(seconds: 4),
      ),
    );
  }
}
