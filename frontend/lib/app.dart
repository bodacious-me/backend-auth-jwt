import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gymbroz/features/auth/data/auth_repo_methods.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:gymbroz/features/auth/presentation/pages/signup_page.dart';
import 'package:gymbroz/features/home/home_page.dart';

class MyApp extends StatelessWidget {
  final authRepoMethods = AuthRepoMethods();

  MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<AuthBloc>(
            create: (context) => AuthBloc(authRepo: authRepoMethods)),
      ],
      child: MaterialApp(
        theme: ThemeData.dark(),
        debugShowCheckedModeBanner: false,
        home: SignUpPage(),
        routes: {
           '/home': (context) => HomePage(),
        },
      ),
    );
  }
}
