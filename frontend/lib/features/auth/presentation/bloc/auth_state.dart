abstract class AuthState {}

class AuthInitialState extends AuthState {}

class AuthenticatedState extends AuthState {
  final String userId;
  AuthenticatedState(this.userId);
}

class UnAuthenticatedState extends AuthState {}

class AuthLoadingState extends AuthState {}

class AuthErrrorState extends AuthState {
  final String errorMessage;
  AuthErrrorState(this.errorMessage);
}

// class AuthCUrrentUserState {
//   String userEmail;
//   AuthCUrrentUserState(this.userEmail);
// }