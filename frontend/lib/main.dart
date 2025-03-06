import 'package:flutter/material.dart';
import 'package:gymbroz/app.dart';
import 'package:gymbroz/features/auth/domain/repositories/auth_repo.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_state.dart';
import 'package:supabase_flutter/supabase_flutter.dart';

void main() async{
   WidgetsFlutterBinding.ensureInitialized();
  await Supabase.initialize(
    url: "https://jrzgausufhzveqsupsve.supabase.co",
    anonKey:
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpyemdhdXN1Zmh6dmVxc3Vwc3ZlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg0MDc3NzIsImV4cCI6MjA1Mzk4Mzc3Mn0.gDWH7EcPIiUr1Ar-epU1yhxrii_ZxD5kF26FWBtFx_o",
  );

  runApp(MyApp());
}
