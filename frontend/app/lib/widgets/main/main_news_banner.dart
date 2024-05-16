import 'package:app/main.dart';
import 'package:app/view_models/main/news_view_model.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:url_launcher/url_launcher.dart';

class MainNewsBanner extends StatefulWidget {
  const MainNewsBanner({super.key});

  @override
  State<MainNewsBanner> createState() => _MainNewsBannerState();
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
      items: [0, 1, 2, 3, 4].map(
        (index) {
          return Builder(
            builder: (context) {
              final viewModel = Provider.of<NewsViewModel>(context);
              return Padding(
                padding: const EdgeInsets.all(8.0),
                child: SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: viewModel.news.isEmpty
                        ? Image.asset('assets/images/loading_icon.png')
                        : InkWell(
                            onTap: () async {
                              // debugPrint('${viewModel.news[index].link}클릭');
                              final url = Uri.parse(
                                viewModel.news[index].link,
                              );
                              if (await canLaunchUrl(url)) {
                                launchUrl(url);
                              } else {
                                debugPrint("Can't launch $url");
                              }
                            },
                            child: Column(
                              children: [
                                SizedBox(
                                    width: MediaQuery.of(context).size.width,
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.start,
                                      children: [
                                        Flexible(
                                            child: RichText(
                                          overflow: TextOverflow.ellipsis,
                                          maxLines: 1,
                                          text: TextSpan(
                                              text: viewModel.news[index].title,
                                              style: const TextStyle(
                                                  fontWeight: FontWeight.bold,
                                                  color: Colors.black,
                                                  fontSize: 18)),
                                        ))
                                      ],
                                    )),
                                const SizedBox(
                                  height: 10,
                                ),
                                Row(
                                  children: [
                                    Column(
                                      children: [
                                        const SizedBox(
                                          height: 10,
                                        ),
                                        SizedBox(
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
                                                  overflow:
                                                      TextOverflow.ellipsis,
                                                  maxLines: 3,
                                                  text: TextSpan(
                                                      text: viewModel
                                                          .news[index].preview,
                                                      style: const TextStyle(
                                                          color: Colors.black)),
                                                ))
                                              ],
                                            )),
                                        const SizedBox(
                                          height: 10,
                                        ),
                                        Row(
                                          children: [
                                            Text(viewModel.news[index].author),
                                            const Text(' · '),
                                            Text(viewModel.news[index].date)
                                          ],
                                        )
                                      ],
                                    ),
                                    Image.network(
                                      viewModel.news[index].thum == null
                                          ? 'https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg'
                                          : viewModel.news[index].thum!,
                                      width: 85,
                                      height: 100,
                                    )
                                  ],
                                ),
                              ],
                            ),
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
