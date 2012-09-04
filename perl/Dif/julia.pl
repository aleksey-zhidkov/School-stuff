sub getNode($$) #функция фоздания узлов
{
  my $deph =  $_[0]; # глубина узла
  my $side = $_[1]; # сторона для предка
  if ($deph==4) {return undef;}; # если глубина равна предельно допустимой+1 возвращяемся
  my %node; # создание хэща-узла
  print 'Create node on the level '.$deph."\n"; 
  $node{'data'}=$deph." ".$side;
  print 'Create son on the left side to node on '.$deph." level\n";
  $node{'left'} = getNode($deph+1,'left'); # рукрсивное создание левого сына
  print 'Create son on the right side to node on '.$deph." level\n";
  $node{'right'} = getNode($deph+1,'right'); #---||---
  return \%node; # возврат ссылки на созданный узел
} 

sub walk($)# функция обхода дерева
{
 my $node = $_[0]; # получение ссылки на узел
 if ($node == undef) {return}; # если ссылка неопределена, то выходим
 print $node->{'data'}."\n"; # вывод значения на экран
 walk($node->{'left'}); # рекурсивынй переход к левому сыну
 walk($node->{'right'});# рекурсивынй переход к правому сыну
}
# главная программа
my %root; # создание корневого ущла
print "Create root\n";
$root{'left'} = getNode(2,'left'); # создание левого сына
$root{'right'} = getNode(2,'right');
$root{'data'}='root';
my $rootref = \%root; # создание ссылки на корень
print "Tree was build\n";
walk($rootref); # обход дерева
