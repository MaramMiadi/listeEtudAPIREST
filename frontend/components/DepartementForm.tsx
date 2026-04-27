"use client";

import { useState } from "react";
import { Departement } from "@/types";

interface DepartementFormProps {
  initialData?: Departement;
  onSuccess: () => void;
  onCancel: () => void;
}

export default function DepartementForm({ initialData, onSuccess, onCancel }: DepartementFormProps) {
  const [nom, setNom] = useState(initialData?.nom || "");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    const url = initialData 
      ? `http://localhost:8080/api/departements/${initialData.id}`
      : "http://localhost:8080/api/departements";
    
    const method = initialData ? "PUT" : "POST";

    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nom }),
      });

      if (res.ok) {
        onSuccess();
      } else {
        alert("Une erreur est survenue");
      }
    } catch (error) {
      console.error(error);
      alert("Erreur de connexion");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
      <h3 className="text-lg font-bold mb-4">
        {initialData ? "Modifier" : "Ajouter"} un Département
      </h3>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-slate-700 mb-1">
            Nom du département
          </label>
          <input
            type="text"
            required
            value={nom}
            onChange={(e) => setNom(e.target.value)}
            className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
            placeholder="Ex: Informatique"
          />
        </div>
        <div className="flex gap-3 pt-2">
          <button
            type="submit"
            disabled={loading}
            className="flex-1 bg-blue-600 text-white py-2 rounded-lg font-medium hover:bg-blue-700 disabled:opacity-50 transition-colors"
          >
            {loading ? "Chargement..." : "Enregistrer"}
          </button>
          <button
            type="button"
            onClick={onCancel}
            className="flex-1 bg-slate-100 text-slate-700 py-2 rounded-lg font-medium hover:bg-slate-200 transition-colors"
          >
            Annuler
          </button>
        </div>
      </div>
    </form>
  );
}
