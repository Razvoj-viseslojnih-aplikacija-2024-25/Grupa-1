import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PorudzbinaService } from '../../../services/porudzbina-service';
import { Porudzbina } from '../../../models/porudzbina';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { Dobavljac } from '../../../models/dobavljac';
import { DobavljacService } from '../../../services/dobavljac-service';

@Component({
  selector: 'app-porudzbina-dialog',
  imports: [
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
  ],
  templateUrl: './porudzbina-dialog.html',
  styleUrl: './porudzbina-dialog.css',
})
export class PorudzbinaDialog implements OnInit {
  flag!: number;
  dobavljaci!: Dobavljac[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<PorudzbinaDialog>,
    private porudzbinaService: PorudzbinaService,
    @Inject(MAT_DIALOG_DATA) public data: Porudzbina,
    private dobavljacService: DobavljacService
  ) {}

  ngOnInit(): void {
    this.dobavljacService.getAllDobavljacs().subscribe((data) => {
      this.dobavljaci = data;
    });
  }

  public add(): void {
    this.porudzbinaService.createPorudzbina(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Porudzbina with naziv: ${data.id} has been successfully created!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while creating a new instance of Porudzbina',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public update(): void {
    this.porudzbinaService.updatePorudzbina(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Porudzbina with naziv: ${data.id} has been successfully updated!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while updating a new instance of Porudzbina',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public delete(): void {
    this.porudzbinaService.deletePorudzbina(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'Okay', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while deleting selected instance of Porudzbina',
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

  public compare(a: Porudzbina, b: Porudzbina) {
    return a.id == b.id;
  }
}
