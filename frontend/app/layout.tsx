import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import Link from "next/link";
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Gestion Étudiants - Université",
  description: "Système de gestion des étudiants et départements",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="fr">
      <body className={`${geistSans.variable} ${geistMono.variable} antialiased bg-slate-50 text-slate-900`}>
        <nav className="bg-white border-b border-slate-200 sticky top-0 z-50">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between h-16">
              <div className="flex items-center">
                <Link href="/" className="text-2xl font-bold text-blue-600">
                  EduManage
                </Link>
                <div className="hidden sm:ml-10 sm:flex sm:space-x-8">
                  <Link 
                    href="/etudiants" 
                    className="inline-flex items-center px-1 pt-1 text-sm font-medium text-slate-900 border-b-2 border-transparent hover:border-blue-500 transition-colors"
                  >
                    Étudiants
                  </Link>
                  <Link 
                    href="/departements" 
                    className="inline-flex items-center px-1 pt-1 text-sm font-medium text-slate-900 border-b-2 border-transparent hover:border-blue-500 transition-colors"
                  >
                    Départements
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </nav>
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          {children}
        </main>
      </body>
    </html>
  );
}
