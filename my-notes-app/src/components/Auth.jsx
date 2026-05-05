import React, { useState } from 'react';
import { loginUser, registerUser } from '../api';
import './Auth.css'; // Importação do arquivo CSS acima

export default function Auth({ onLogin }) {
  const [isRegister, setIsRegister] = useState(false);
  const [form, setForm] = useState({ username: '', email: '', password: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isRegister) {
      await registerUser(form.username, form.email, form.password);
      setIsRegister(false);
    } else {
      const user = await loginUser(form.email, form.password);
      onLogin(user);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <div className="auth-header">
          <h2>{isRegister ? 'Criar conta' : 'Entrar'}</h2>
          <p>
            {isRegister 
              ? 'Organize as suas notas em grafos agora mesmo.' 
              : 'Aceda aos seus documentos e conexões.'}
          </p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          {isRegister && (
            <div className="form-group">
              <label>Nome de utilizador</label>
              <input 
                type="text"
                placeholder="Ex: nicolasyan" 
                onChange={e => setForm({...form, username: e.target.value})} 
                required 
              />
            </div>
          )}
          
          <div className="form-group">
            <label>Email</label>
            <input 
              type="email" 
              placeholder="seu@email.com" 
              onChange={e => setForm({...form, email: e.target.value})} 
              required 
            />
          </div>

          <div className="form-group">
            <label>Palavra-passe</label>
            <input 
              type="password" 
              placeholder="••••••••" 
              onChange={e => setForm({...form, password: e.target.value})} 
              required 
            />
          </div>

          <button className="auth-button" type="submit">
            {isRegister ? 'Finalizar Registo' : 'Entrar'}
          </button>
        </form>

        <div className="auth-footer">
          {isRegister ? 'Já tem uma conta?' : 'Ainda não tem conta?'}
          <button 
            className="toggle-btn" 
            onClick={() => setIsRegister(!isRegister)}
          >
            {isRegister ? 'Inicie sessão' : 'Registe-se'}
          </button>
        </div>
      </div>
    </div>
  );
}