import React, { useState } from 'react';
import { loginUser, registerUser } from '../api';

export default function Auth({ onLogin }) {
  const [isRegister, setIsRegister] = useState(false);
  const [form, setForm] = useState({ username: '', email: '', password: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isRegister) {
      await registerUser(form.username, form.email, form.password); // [cite: 33]
      setIsRegister(false);
    } else {
      const user = await loginUser(form.email, form.password); // [cite: 34]
      onLogin(user);
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <form onSubmit={handleSubmit} className="p-8 bg-white rounded shadow-md w-96">
        <h2 className="text-2xl mb-4">{isRegister ? 'Cadastrar' : 'Entrar'}</h2>
        {isRegister && (
          <input className="w-full p-2 mb-3 border rounded" placeholder="Usuário" 
                 onChange={e => setForm({...form, username: e.target.value})} required />
        )}
        <input className="w-full p-2 mb-3 border rounded" type="email" placeholder="Email" 
               onChange={e => setForm({...form, email: e.target.value})} required />
        <input className="w-full p-2 mb-4 border rounded" type="password" placeholder="Senha" 
               onChange={e => setForm({...form, password: e.target.value})} required />
        <button className="w-full p-2 bg-blue-600 text-white rounded" type="submit">
          {isRegister ? 'Criar Conta' : 'Acessar'}
        </button>
        <p className="mt-4 text-sm text-center text-blue-500 cursor-pointer" 
           onClick={() => setIsRegister(!isRegister)}>
          {isRegister ? 'Já tenho conta' : 'Criar nova conta'}
        </p>
      </form>
    </div>
  );
}