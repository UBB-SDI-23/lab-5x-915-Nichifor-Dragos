import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/core/service/storage.service';
import { AuthService } from 'src/app/core/service/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(
      private router: Router,
      private storageService: StorageService,
      private authService: AuthService,
      private toastrService: ToastrService
  ) {}

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;


  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.username = user.username;
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        this.storageService.clean();
        window.location.reload();
      },
      error: err => {
        this.toastrService.error("Could not log out", '', { progressBar: true })
      }
    });
  }

  getHeaderStyle() {
    if (this.router.url == '/home-page')
      return 'main-page-nav';
    else
      return 'bg-dark';
  }

  getLinkStyle() {
    if (this.router.url == '/home-page')
      return 'main-page-link';
    else
      return '';
  }

  getDropdownStyle() {
    if (this.router.url == '/home-page')
      return 'main-dropdown-menu';
    else
      return '';
  }

}
