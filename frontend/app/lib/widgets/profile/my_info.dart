import 'package:flutter/material.dart';

class MyInfo extends StatefulWidget {
  const MyInfo({super.key});

  @override
  _MyInfoState createState() => _MyInfoState();
}

class _MyInfoState extends State<MyInfo> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.7,
      height: MediaQuery.of(context).size.height * 0.08,
      child: Padding(
        padding: const EdgeInsets.only(left:20.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [Image.asset('assets/images/loading_icon.png'),
          Padding(
            padding: const EdgeInsets.only(left:20.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text('부서', style: TextStyle(fontSize: 20),),
                Row(
                  children: [
                    Text('이름', style: TextStyle(fontSize: 20)),
                    SizedBox(width: 5,),
                    Text('직급', style: TextStyle(fontSize: 20))
                  ],
                )
              ],
            ),
          )],
        ),
      ),
    );
  }
}
