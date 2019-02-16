import { Component } from '@angular/core';
import { LoadingController, AlertController  } from '@ionic/angular';


@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})

export class Tab3Page {
  constructor(public loadingController: LoadingController, public alertController: AlertController) {}
    async presentLoading() {
      const loading = await this.loadingController.create({
        message: 'Hellooo',
        duration: 2000
      });
      await loading.present();
  
      const { role, data } = await loading.onDidDismiss();
  
      console.log('Loading dismissed!');
    }
  
    async presentLoadingWithOptions() {
      const loading = await this.loadingController.create({
        spinner: null,
        duration: 5000,
        message: 'Please wait...',
        translucent: true,
        cssClass: 'custom-class custom-loading'
      });
      return await loading.present();
    }

    async presentAlertRadio() {
      const alert = await this.alertController.create({
        header: 'Radio',
        inputs: [
          {
            name: 'radio1',
            type: 'radio',
            label: 'Estudiante',
            value: 'value1',
            checked: true
          },
          {
            name: 'radio2',
            type: 'radio',
            label: 'Tutor Estudiante',
            value: 'value2'
          },
          {
            name: 'radio3',
            type: 'radio',
            label: 'Profesor',
            value: 'value3'
          },
          {
            name: 'radio4',
            type: 'radio',
            label: 'Tutor + Estudiante',
            value: 'value4'
          },
          
        ],
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              console.log('Confirm Cancel');
            }
          }, {
            text: 'Ok',
            handler: () => {
              console.log('Confirm Ok');
            }
          }
        ]
      });
  
      await alert.present();
    }

    async presentAlertMultipleButtons() {
      const alert = await this.alertController.create({
        header: 'Is Sure?',
        message: 'By accepting terms and conditions allows us to manage your data.',
        buttons: ['Cancel', 'Open Modal', 'Delete']
      });
  
      await alert.present();
    }
}
