
import 'package:google_sign_in/google_sign_in.dart';
import 'package:supabase_flutter/supabase_flutter.dart';

abstract class AuthRepo {
  Future<String?> SignUpWithEmail(String email, String password);
  Future<String?> SignUpWithGoogle();
  Future<String?> AuthSignUpWithApple();
  Future<String?> SignInWithEmail(String email, String password);
  Future<void> SignOut();
  Future<String?> GetCurrentUser();
}