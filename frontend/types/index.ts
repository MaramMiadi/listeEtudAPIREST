export interface Departement {
  id: number;
  nom: string;
  description?: string;
}

export interface Etudiant {
  id: number;
  nom: string;
  cin: string;
  email: string;
  dateNaissance: string;
  anneePremiereInscription: number;
  departementId: number;
  departementNom?: string;
}
