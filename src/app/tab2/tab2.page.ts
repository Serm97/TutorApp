import { Component } from '@angular/core';
import { ActionSheetController, AlertController } from '@ionic/angular';
import { UserServiceService } from '../services/user-service.service';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';

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
    public userService: UserServiceService,
    private storage: Storage,
    private geolocation: Geolocation
  ) { }

  ngOnInit() {
    this.userService.getUsers().subscribe((data) => { // Success
      this.users = data['results'];
      this.storage.set('name', 'Max');
      this.storage.set('Users', this.users);
      this.storage.get('name').then((val) => {
        console.log('name', val);
      });
    },
      (error) => { console.error(error); }
    )
  }

  async presentActionSheet() {
    const actionSheet = await this.actionSheetController.create({
      header: 'Albums',
      buttons: [{
        text: 'Delete',
        role: 'destructive',
        icon: 'trash',
        handler: () => {
          console.log('Delete clicked');
        }
      }, {
        text: 'Share',
        icon: 'share',
        handler: () => {
          console.log('Share clicked');
        }
      }, {
        text: 'Play (open modal)',
        icon: 'arrow-dropright-circle',
        handler: () => {
          console.log('Play clicked');
        }
      }, {
        text: 'Favorite',
        icon: 'heart',
        handler: () => {
          this.geolocation.getCurrentPosition().then((resp) => {
            // resp.coords.latitude
            // resp.coords.longitude
            console.log(resp.coords);
            this.showLocation(resp.coords);
          }).catch((error) => {
            console.log('Error getting location', error);
          });

          let watch = this.geolocation.watchPosition();
          watch.subscribe((data) => {
            // data can be a set of coordinates, or an error (if an error occurred).
            // data.coords.latitude
            // data.coords.longitude
          });
          console.log('Favorite clicked');
        }
      }, {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }]
    });
    await actionSheet.present();
  }

  async showLocation(coords) {
    console.log(coords);
    const alert = await this.alertController.create({      
      header: 'Ubicacion',
      subHeader: '¿Donde estoy?',
      message: 'Tu ubicacion es \n latitud: '
        + coords.latitude
        + '\n Logitud: '
        + coords.longitude
        + '\n Presicion: '
        + coords.accuracy,
      buttons: ['OK']
    });
    await alert.present();
  }

  async hola() {
    console.log('Hola Mundooo');
  }

}
