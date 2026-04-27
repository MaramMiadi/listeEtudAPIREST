import { Etudiant } from "@/types";
import Link from "next/link";

interface EtudiantCardProps {
  etudiant: Etudiant;
  onDelete: (id: number) => void;
}

export default function EtudiantCard({ etudiant, onDelete }: EtudiantCardProps) {
  return (
    <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-100 hover:shadow-md transition-shadow">
      <div className="flex justify-between items-start">
        <div>
          <h3 className="text-xl font-bold text-slate-800">{etudiant.nom}</h3>
          <p className="text-sm text-slate-500 mt-1">CIN: {etudiant.cin}</p>
        </div>
        <span className="bg-blue-50 text-blue-600 text-xs font-semibold px-2.5 py-1 rounded-full">
          {etudiant.departementNom || `Dept #${etudiant.departementId}`}
        </span>
      </div>
      
      <div className="mt-4 space-y-2">
        <div className="flex items-center text-sm text-slate-600">
          <span className="font-medium mr-2">Email:</span> {etudiant.email}
        </div>
        <div className="flex items-center text-sm text-slate-600">
          <span className="font-medium mr-2">Né(e) le:</span> {etudiant.dateNaissance}
        </div>
        <div className="flex items-center text-sm text-slate-600">
          <span className="font-medium mr-2">Inscription:</span> {etudiant.anneePremiereInscription}
        </div>
      </div>

      <div className="mt-6 flex gap-3">
        <Link 
          href={`/etudiants/${etudiant.id}`}
          className="flex-1 text-center py-2 text-sm font-medium text-blue-600 bg-blue-50 rounded-lg hover:bg-blue-100 transition-colors"
        >
          Modifier
        </Link>
        <button 
          onClick={() => onDelete(etudiant.id)}
          className="flex-1 py-2 text-sm font-medium text-red-600 bg-red-50 rounded-lg hover:bg-red-100 transition-colors"
        >
          Supprimer
        </button>
      </div>
    </div>
  );
}
