import { Component } from '@angular/core';
import { ActionSheetController, AlertController } from '@ionic/angular';
import { UserServiceService } from '../services/user-service.service';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  users: any[] = [];

  constructor(
    public actionSheetController: ActionSheetController,
    public alertController: AlertController,
    public userService: UserServiceService
  ) { }

  ngOnInit() {
    this.userService.getUsers().subscribe((data) => { // Success
      this.users = data['results'];
      console.log(this.users);
    },
      (error) => { console.error(error); }
    )
  }

}
