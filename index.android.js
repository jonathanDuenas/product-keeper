var modules={'react-native': require('react-native'), 'react': require('react')};modules['./images/cljs.png']=require('./images/cljs.png');
require('figwheel-bridge').withModules(modules).start('ProductKeeper','android','10.0.2.2');