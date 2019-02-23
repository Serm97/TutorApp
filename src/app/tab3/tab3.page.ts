import { Component } from '@angular/core';
import { LoadingController, AlertController  } from '@ionic/angular';
import​ { RegisterService } from '../../app/services/register.service';    


@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})

export class Tab3Page {
  constructor(public loadingController: LoadingController, 
    public alertController: AlertController,
    public registerService: RegisterService) {}
    
    usersRegister: any[] = [];

    ionViewDidLoad(){    
      this.registerService.getUsersRegister()   
      .subscribe(      
          (data) => { // Success        
            this.usersRegister = data['results'];
            console.log(this.usersRegister);
               
          },
          (error) =>{        
            console.error(error);      
          }    
        ) 
      }
    
    async presentLoading() {
      
      const loading = await this.loadingController.create({
        message: 'Validate form...',
        duration: 2000
      });
      
      await loading.present();
  
      const { role, data } = await loading.onDidDismiss();
  
      swal('Success!', 'This request has been sent.', 'success');
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

    async presentAlertMultipleButtons(type) {
       let list = ''; 
       let title = '';

       if(type == 'tutor'){
        list = '<ion-list><ion-radio-group><ion-item><ion-label>Juan Mendoza</ion-label><ion-radio value="0"></ion-radio></ion-item><ion-item><ion-label>Francisco Reyes</ion-label><ion-radio value="#"></ion-radio></ion-item></ion-radio-group></ion-list>';
        title = 'Tutors Compatible';
       }else{
         list = '<ion-list><ion-radio-group><ion-item><ion-label>Programming</ion-label><ion-radio value="0"></ion-radio></ion-item><ion-item><ion-label>Database</ion-label><ion-radio value="#"></ion-radio></ion-item><ion-item><ion-label>Design Apps</ion-label><ion-radio value="#"></ion-radio></ion-item></ion-radio-group></ion-list>';
         title = 'Your Subjects';
       }

      const alert = await this.alertController.create({
        header: title,
        message: list,
        buttons: ['Choose']
      });
  
      await alert.present();
    }
}
