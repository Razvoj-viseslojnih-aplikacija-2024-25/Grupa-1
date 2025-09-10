import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DobavljacService } from '../../../services/dobavljac-service';
import { Dobavljac } from '../../../models/dobavljac';

@Component({
  selector: 'app-dobavljac-dialog',
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './dobavljac-dialog.html',
  styleUrl: './dobavljac-dialog.css',
})
export class DobavljacDialog {
  flag!: number;

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<DobavljacDialog>,
    private dobavljacService: DobavljacService,
    @Inject(MAT_DIALOG_DATA) public data: Dobavljac
  ) {}

  public add(): void {
    this.dobavljacService.createDobavljac(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Dobavljac with naziv: ${data.naziv} has been successfully created!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while creating a new instance of Dobavljac',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public update(): void {
    this.dobavljacService.updateDobavljac(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Dobavljac with naziv: ${data.naziv} has been successfully updated!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while updating a new instance of Dobavljac',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public delete(): void {
    this.dobavljacService.deleteDobavljac(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'Okay', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while deleting selected instance of Dobavljac',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('You have given up on changes', 'Okay', { duration: 2500 });
  }
}
