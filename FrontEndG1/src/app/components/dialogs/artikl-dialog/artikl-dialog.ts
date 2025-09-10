import { Component, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Artikl } from '../../../models/artikl';
import { ArtiklService } from '../../../services/artikl-service';


@Component({
  selector: 'app-artikl-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule,FormsModule, CommonModule],
  templateUrl: './artikl-dialog.html',
  styleUrl: './artikl-dialog.css'
})
export class ArtiklDialog {

  flag!: number;

  constructor(
    private snackBar:MatSnackBar,
    private dialogRef: MatDialogRef<ArtiklDialog>,
    private artiklService:ArtiklService,
    @Inject(MAT_DIALOG_DATA) public data: Artikl 
  ){}

  public add():void{
    this.artiklService.createArtikl(this.data).subscribe(
      {
        next: (data)=> {
          this.dialogRef.close(1);
          this.snackBar.open(`Artikl with naziv: ${data.naziv} has been successfully created!`, 'Okay', {duration:2500});
        },
        error:error => {
          this.snackBar.open('There was an issue while creating a new instance of Artikl', 'Okay', {duration:2500});
          console.log(error.message);
        }
      }
    )
  }

  public update():void{
    this.artiklService.updateArtikl(this.data).subscribe(
      {
        next: (data)=> {
          this.dialogRef.close(1);
          this.snackBar.open(`Artikl with naziv: ${data.naziv} has been successfully updated!`, 'Okay', {duration:2500});
        },
        error:error => {
          this.snackBar.open('There was an issue while updating a new instance of Artikl', 'Okay', {duration:2500});
          console.log(error.message);
        }
      }
    )
  }

  public delete():void{
    this.artiklService.deleteArtikl(this.data.id).subscribe(
      {
        next: (response)=> {
          this.dialogRef.close(1);
          this.snackBar.open(response, 'Okay', {duration:2500});
        },
        error:error => {
          this.snackBar.open('There was an issue while deleting selected instance of Artikl', 'Okay', {duration:2500});
          console.log(error.message);
        }
      }
    )
  }

  public cancel():void{
    this.dialogRef.close();
    this.snackBar.open('You have given up on changes', 'Okay', {duration:2500});
  }

}
