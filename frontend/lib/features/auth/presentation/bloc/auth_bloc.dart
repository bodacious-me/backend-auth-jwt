import 'package:bloc/bloc.dart';
import 'package:gymbroz/features/auth/domain/repositories/auth_repo.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_event.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final AuthRepo authRepo;
  AuthBloc({required this.authRepo}) : super((AuthInitialState())) {
    on<AuthSignUpWithAppleEvent>((event, emit) {
      emit(AuthLoadingState());
      emit(AuthErrrorState('we do not support this method currently'));
    });

    on<AuthSignUpWithEmailEvent>((event, emit)async {
      emit(AuthLoadingState());
      try {
        final response = await authRepo.SignUpWithEmail(event.email, event.password);
        if (response.toString().isNotEmpty)
          emit(AuthenticatedState(response.toString()));
        else
          emit(UnAuthenticatedState());
      } catch (e) {
        emit(AuthErrrorState(e.toString()));
      }
    });


    on<AuthSignUpWithGoogleEvent>((event, emit) async {
      emit(AuthLoadingState());
      try {
        final currentUserEmail = await  authRepo.SignUpWithGoogle();
        if (currentUserEmail!.isNotEmpty)
          emit(AuthenticatedState(currentUserEmail));
        else
          emit(UnAuthenticatedState());
      } catch (e) {
        emit(AuthErrrorState(e.toString()));
      }
    });

    on<AuthSignInWithEmailEvent>((event, emit) async {
      emit(AuthLoadingState());
      try {
        final response =
            await authRepo.SignInWithEmail(event.email, event.password);
        if (response.toString().isNotEmpty)
          emit(AuthenticatedState(response.toString()));
        else
          emit(UnAuthenticatedState());
      } catch (e) {
        emit(AuthErrrorState(e.toString()));
      }
    });

    on<AuthSignOutEvent>((event, emit) async {
      emit(AuthLoadingState());
      try {
        await authRepo.SignOut();
      } catch (e) {
        emit(AuthErrrorState(e.toString()));
      }
    });
  }
}
