import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string;
  password: string;

  constructor(private authService: AuthService, private snackBar: MatSnackBar, private router: Router, private cookieService: CookieService) {
    this.username = '';
    this.password = '';
  }

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        this.cookieService.set('token', response.token, response.expirationTime);
        this.cookieService.set('expirationTime', new Date(response.expirationTime).toUTCString());
        this.router.navigate(['/principal']);
      },
      error => {
        this.username = '';
        this.password = '';
        this.snackBar.open('Erro ao fazer login. Verifique seu usuário e senha.', 'Fechar', { duration: 5000 });
      }
    );
  }
}
