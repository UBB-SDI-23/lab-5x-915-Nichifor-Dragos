import { Component, OnInit } from '@angular/core';
import { User, UserAdminPage } from 'src/app/core/model/user.model';
import {FormBuilder} from '@angular/forms';
import { SQLResponse } from 'src/app/core/model/user.model';

import { debounceTime, Subject } from 'rxjs';
import { StorageService } from 'src/app/core/service/storage.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/core/service/user.service';
import { Router } from '@angular/router';
import { SqlService } from 'src/app/core/service/sql.service';

@Component({
  selector: 'app-admin-board',
  templateUrl: './admin-board.component.html',
  styleUrls: ['./admin-board.component.css']
})
export class AdminBoardComponent implements OnInit{

  currentUser?: User
  isLoggedIn = false;

  showEdit = false;

  selectedOption?: string;
  selectedUser?: UserAdminPage;
  searchTerm = new Subject<string>();
  options?: UserAdminPage[];

  roles = this.formBuilder.group({
    user: false,
    moderator: false,
    admin: false,
  });

  constructor (
    private storageService: StorageService,
    private toastrService: ToastrService,
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder,
    private sqlService: SqlService
  ) {}

  ngOnInit() {
    if(!this.storageService.isLoggedIn()) {
      this.toastrService.error("Logging in as admin is required", '', { progressBar: true }); this.onBackToHomePage() 
    } else {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser()
      if (!this.currentUser || !this.currentUser.roles.includes("ROLE_ADMIN")) {
        this.toastrService.error("Logging in as admin is required", '', { progressBar: true }); this.onBackToHomePage() 
      }
    }
    this.searchTerm.pipe(
      debounceTime(1000)).subscribe(term => {
        if (term.trim()) {
          this.userService.getUsersByName(term).subscribe({
            next: (users: UserAdminPage[]) => {
              this.options = users; }});
        } else { this.options = undefined; }
    });
  }

  onSelection(event: any): void {
    this.selectedOption = event.option.value.username;
    this.selectedUser = event.option.value;
    this.showEdit = true;
    if (this.selectedUser?.roles) {
      this.roles.setValue({
        user: this.selectedUser.roles.some((role: any) => role.name === "ROLE_USER"),
        moderator: this.selectedUser.roles.some((role: any) => role.name === "ROLE_MODERATOR"),
        admin: this.selectedUser.roles.some((role: any) => role.name === "ROLE_ADMIN")
      });
    }
    console.log(this.selectedUser)
  }

  search(term: string): void {
    this.searchTerm.next(term);
    this.showEdit = false;
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

  onUpdateRoles() {
    const isUser = this.roles.get('user')?.value;
    const isModerator = this.roles.get('moderator')?.value
    const isAdmin = this.roles.get('admin')?.value

    const roles = { isUser, isModerator, isAdmin }

    console.log(roles)
    if (this.selectedUser) {
    this.userService.updateUserRoles(this.selectedUser.id, roles).subscribe(
      (response) => { this.toastrService.success("User roles updated successfully", '', { progressBar: true });},
      (error) => { this.toastrService.error("Could not update user roles", '', { progressBar: true }); });
    }
  }

  // ====================================================== DASHBOARD ======================================================

  messages: string[] = [];

  deleteRaces() {
    this.messages.push('Deleting races...');
    this.sqlService.deleteAllRaces().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {},
    });
  }

  insertRaces() {
    this.messages.push('Inserting races...');
    this.sqlService.insertAllRaces().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  deletePilots() {
    this.messages.push('Deleting pilots...');
    this.sqlService.deleteAllPilots().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  insertPilots() {
    this.messages.push('Inserting pilots...');
    this.sqlService.insertAllPilots().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  deleteCars() {
    this.messages.push('Deleting cars...');
    this.sqlService.deleteAllCars().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  insertCars() {
    this.messages.push('Inserting cars...');
    this.sqlService.insertAllCars().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  deleteParticipations() {
    this.messages.push('Deleting participations...');
    this.sqlService.deleteAllParticipations().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {},
    });
  }

  insertParticipations() {
    this.messages.push('Inserting participations...');
    this.sqlService.insertAllParticipations().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {},
    });
  }

  deleteAll() {
    this.messages.push('Deleting participations...');
    this.sqlService.deleteAllParticipations().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {
        this.messages.push('Deleting races...');
        this.sqlService.deleteAllRaces().subscribe({
          next: (message: SQLResponse) => {
            this.messages.push(message.message);
          },
          error: (error) => {
            console.log(error);
          },
          complete: () => {
            this.messages.push('Deleting cars...');
            this.sqlService.deleteAllCars().subscribe({
              next: (message: SQLResponse) => {
                this.messages.push(message.message);
              },
              error: (error) => {},
              complete: () => {
                this.messages.push('Deleting pilots...');
                this.sqlService.deleteAllPilots().subscribe({
                  next: (message: SQLResponse) => {
                    this.messages.push(message.message);
                  },
                  error: (error) => {
                    console.log(error);
                  },
                  complete: () => {},
                });
              },
            });
          },
        });
      },
    });
  }

  insertAll() {
    this.messages.push('Inserting races...');
    this.sqlService.insertAllRaces().subscribe({
      next: (message: SQLResponse) => {
        this.messages.push(message.message);
      },
      error: (error) => {},
      complete: () => {
        this.messages.push('Inserting pilots...');
        this.sqlService.insertAllPilots().subscribe({
          next: (message: SQLResponse) => {
            this.messages.push(message.message);
          },
          error: (error) => {},
          complete: () => {
            this.messages.push('Inserting cars...');
            this.sqlService.insertAllCars().subscribe({
              next: (message: SQLResponse) => {
                this.messages.push(message.message);
              },
              error: (error) => {},
              complete: () => {
                this.messages.push('Inserting participations...');
                this.sqlService.insertAllParticipations().subscribe({
                  next: (message: SQLResponse) => {
                    this.messages.push(message.message);
                  },
                  error: (error) => {},
                  complete: () => {},
                });
              },
            });
          },
        });
      },
    });
  }

  clearConsole() {
    this.messages = [];
  }

}

