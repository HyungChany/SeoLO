import 'package:app/main.dart';
import 'package:app/widgets/common_text_button.dart';
import 'package:carousel_slider/carousel_options.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';

class MainNewsBanner extends StatefulWidget {
  const MainNewsBanner({super.key});

  @override
  _MainNewsBannerState createState() => _MainNewsBannerState();
}

class _MainNewsBannerState extends State<MainNewsBanner> {
  int _current = 0;
  final CarouselController _controller = CarouselController();

  final List<String> titleList = [
    "[단독]중대재해 줄인다더니…'안전보건 인증' 없애려는 정부",
    "한일시멘트, 'CSO 안전 포럼' 개최…\"안전 의식 강화\"",
    "박경국 가스안전公 사장, \"‘에너지 안전 종합기관’ 도약” 강조",
    "김제시, 안전취약시설 86개소 대상 집중 안전점검 추진",
    "가스안전公 \"안전관리에 AI 접목…수소 전주기 안전관리\"",
  ];

  final List<String> previewList = [
    "[이데일리 서대웅 기자] 정부가 중대 산업사고 예방을 위해 인증하는 ‘안전보건 경영시스템’ 인증제도를 폐기하는 방안을 검토 중인 것으로 파악됐다. 중대재해 감축에 나서야 하는 정부가 정반대 방향으로 정책을 펼치고 있다는 지적이 나왔다. (사진=연합뉴스...",
    "[서울=뉴시스] 한일시멘트 CSO 안전포럼. [서울=뉴시스] 박성환 기자 = 한일시멘트는 지난 23일 서울 서초동 본사에서 임직원들의 안전 의식을 높이기 위해 'CSO(최고안전경영자) 안전 포럼'을 개최했다고 24일 밝혔다. 이날 포럼에는 오해근 CSO, 한일시멘트 및...",
    "(지디넷코리아=주문정 기자)박경국 가스안전공사 사장은 23일 “‘에너지 안전 종합기관’을 실현해 나갈 계획”이라고 밝혔다. 박 사장은 이날 취임 100일 간담회에서 “인공지능(AI)·로봇 등 첨단기술을 접목한 검사·진단, 재난관리 혁신을 통해 미래지향적 가스...",
    "6월 21일까지 김제시가 오는 6월 21일까지 2024년 대한민국 안전대전환을 위해 재난이나 사고 발생이 우려되는 안전취약시설 86개소를 대상으로 집중 안전점검을 실시한다./김제시 [더팩트 | 김제=전광훈 기자] 전북 김제시(시장 정성주)가 오는 6월 21...",
    "박경국 한국가스안전공사 사장 한국가스안전공사가 올해 창립 50주년을 맞아 '에너지 안전 종합기관'으로 거듭난다. 안전관리를 디지털로 전환하고 인공지능(AI)과 로봇 등 첨단기술을 접목한다. 박경국 한국가스안전공사 사장은 23일 기자간담회를 열고 \"안전사고...",
  ];

  final List<String> authorList = [
    "이데일리",
    "뉴시스",
    "지디넷코리아",
    "더팩트",
    "머니투데이",
  ];

  final List<String> dateList = [
    "2024.04.16",
    "3시간전",
    "21시간전",
    "3시간전",
    "21시간전",
  ];

  final List<String> thumList = [
    "https://search1.kakaocdn.net/argon/200x200_85_c/9vSBMekFpGI0",
    "https://search2.kakaocdn.net/argon/200x200_85_c/Ey5xofJggKV0",
    "https://search2.kakaocdn.net/argon/200x200_85_c/EJRFlRbJj750",
    "https://search3.kakaocdn.net/argon/200x200_85_c/JK5jYVwnM2S0",
    "https://search4.kakaocdn.net/argon/200x200_85_c/CMWiiyGYJW30",
  ];

  final List<String> linkList = [
    "http://v.daum.net/v/20240416060132645",
    "http://v.daum.net/v/20240424095935668",
    "http://v.daum.net/v/20240423155116543",
    "http://v.daum.net/v/20240424100503996",
    "http://v.daum.net/v/20240423162226983"
  ];

  @override
  Widget build(BuildContext context) {
    // var viewModel = Provider.of<PlayerViewModel>(context);
    return Container(
        height: MediaQuery
            .of(context)
            .size
            .height * 0.18,
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
              return Padding(
                padding: const EdgeInsets.all(8.0),
                child: SizedBox(
                    width: MediaQuery
                        .of(context)
                        .size
                        .width,
                    child: Column(
                      children: [
                        Container(
                            width: MediaQuery
                                .of(context)
                                .size
                                .width,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Flexible(
                                    child: RichText(
                                      overflow: TextOverflow.ellipsis,
                                      maxLines: 1,
                                      text: TextSpan(
                                          text: titleList[index],
                                          style: TextStyle(
                                              fontWeight: FontWeight.bold,
                                              color: Colors.black,
                                              fontSize: 18)),
                                    ))
                              ],
                            )),
                        SizedBox(height: 10,),
                        Row(
                          children: [
                            Column(
                              children: [
                                SizedBox(
                                  height: 10,
                                ),
                                Container(
                                    width:
                                    MediaQuery
                                        .of(context)
                                        .size
                                        .width * 0.6,
                                    child: Row(
                                      mainAxisAlignment:
                                      MainAxisAlignment.start,
                                      children: [
                                        Flexible(
                                            child: RichText(
                                              overflow: TextOverflow.ellipsis,
                                              maxLines: 3,
                                              text: TextSpan(
                                                  text: previewList[index],
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
                                    Text(authorList[index]),
                                    Text(' · '),
                                    Text(dateList[index])
                                  ],
                                )
                              ],
                            ),
                            Image.network(
                              thumList[index],
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
        onPageChanged: (index, reason) {
          setState(() {
            _current = index;
          });
        },
      ),
    );
  }
}