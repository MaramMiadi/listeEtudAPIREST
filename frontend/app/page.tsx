import Link from "next/link";

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[70vh] text-center space-y-12">
      <div className="space-y-4 max-w-3xl">
        <h1 className="text-5xl md:text-6xl font-extrabold tracking-tight text-slate-900 leading-tight">
          Gérez votre campus en <span className="text-blue-600">toute simplicité</span>.
        </h1>
        <p className="text-xl text-slate-500">
          Une plateforme moderne pour la gestion des étudiants et des départements, 
          conçue pour l'efficacité et la rapidité.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 w-full max-w-4xl">
        <Link 
          href="/etudiants"
          className="group bg-white p-8 rounded-3xl shadow-sm border border-slate-200 hover:shadow-xl hover:border-blue-200 transition-all text-left"
        >
          <div className="w-12 h-12 bg-blue-100 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
            <svg className="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
          </div>
          <h2 className="text-2xl font-bold mb-2">Gestion Étudiants</h2>
          <p className="text-slate-500 mb-6">Consultez la liste, modifiez les dossiers et gérez les nouvelles inscriptions.</p>
          <span className="text-blue-600 font-bold inline-flex items-center group-hover:translate-x-1 transition-transform">
            Accéder aux étudiants &rarr;
          </span>
        </Link>

        <Link 
          href="/departements"
          className="group bg-white p-8 rounded-3xl shadow-sm border border-slate-200 hover:shadow-xl hover:border-blue-200 transition-all text-left"
        >
          <div className="w-12 h-12 bg-indigo-100 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
            <svg className="w-6 h-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-7h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
            </svg>
          </div>
          <h2 className="text-2xl font-bold mb-2">Départements</h2>
          <p className="text-slate-500 mb-6">Gérez les départements académiques et leur structure.</p>
          <span className="text-indigo-600 font-bold inline-flex items-center group-hover:translate-x-1 transition-transform">
            Accéder aux départements &rarr;
          </span>
        </Link>
      </div>

      <div className="pt-12 flex items-center gap-4 text-sm text-slate-400">
        <span className="w-2 h-2 bg-green-500 rounded-full animate-pulse"></span>
        Connecté à l'API Gateway : http://localhost:8080
      </div>
    </div>
  );
}
