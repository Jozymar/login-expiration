import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent {

  constructor(private cookieService: CookieService, private router: Router) { }

  logout(): void {
    this.cookieService.delete('token');
    this.cookieService.delete('expirationTime');
    this.router.navigate(['/login']);
  }
}
