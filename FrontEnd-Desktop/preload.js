window.addEventListener('DOMContentLoaded', () => {
    const replaceText = (selector, text) => {
      const element = document.getElementById(selector)
      if (element) element.innerText = text
    }
  
    for (const dependency of ['chrome', 'node', 'electron','macos']) {//añadido macos para prueba
      replaceText(`${dependency}-version`, process.versions[dependency])
    }
  })