import 'package:app/view_models/user/app_lock_state.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/navigator/common_navigation_bar.dart';
import 'package:app/widgets/profile/logout_button.dart';
import 'package:app/widgets/profile/my_activity.dart';
import 'package:app/widgets/profile/my_info.dart';
import 'package:app/widgets/profile/my_loto.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> with WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.paused ||
        state == AppLifecycleState.detached) {
      Provider.of<AppLockState>(context, listen: false)
          .lock(ModalRoute.of(context)!.settings.name!);
    }
  }

  int _selectedIndex = 2;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    if (index == 0) {
      Navigator.pushReplacementNamed(context, '/main');
    }
    if (index == 1) {
      Navigator.pushNamed(context, '/bluetooth');
      _selectedIndex = 2;
    }
    if (index == 2) {
      Navigator.pushReplacementNamed(context, '/profile');
    }
  }

  titleStyle() {
    return const TextStyle(fontWeight: FontWeight.bold, fontSize: 25);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const Header(title: '프로필', back: false),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(
            height: 20,
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                MyInfo(),
                LogoutButton()],
            ),
          ),
          const Divider(color: Colors.grey, thickness: 1.0,),
          Padding(
            padding: const EdgeInsets.only(left: 20.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('내 활동', style: titleStyle()),
                const SizedBox(height: 10,),
                const MyActivity(),
              ],
            ),
          ),
          const Divider(color: Colors.grey, thickness: 1.0,),
          Padding(
            padding: const EdgeInsets.only(left: 20.0),
            child: Text('나의 LOTO', style: titleStyle()),
          ),
          const SizedBox(height: 10,),
          MyLoto()
        ],
      ),
      bottomNavigationBar: CustomBottomNavigationBar(
        selectedIndex: _selectedIndex,
        onItemTapped: _onItemTapped,
      ),
    );
  }
}