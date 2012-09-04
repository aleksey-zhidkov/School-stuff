sub getNode($$) #������� �������� �����
{
  my $deph =  $_[0]; # ������� ����
  my $side = $_[1]; # ������� ��� ������
  if ($deph==4) {return undef;}; # ���� ������� ����� ��������� ����������+1 ������������
  my %node; # �������� ����-����
  print 'Create node on the level '.$deph."\n"; 
  $node{'data'}=$deph." ".$side;
  print 'Create son on the left side to node on '.$deph." level\n";
  $node{'left'} = getNode($deph+1,'left'); # ���������� �������� ������ ����
  print 'Create son on the right side to node on '.$deph." level\n";
  $node{'right'} = getNode($deph+1,'right'); #---||---
  return \%node; # ������� ������ �� ��������� ����
} 

sub walk($)# ������� ������ ������
{
 my $node = $_[0]; # ��������� ������ �� ����
 if ($node == undef) {return}; # ���� ������ ������������, �� �������
 print $node->{'data'}."\n"; # ����� �������� �� �����
 walk($node->{'left'}); # ����������� ������� � ������ ����
 walk($node->{'right'});# ����������� ������� � ������� ����
}
# ������� ���������
my %root; # �������� ��������� ����
print "Create root\n";
$root{'left'} = getNode(2,'left'); # �������� ������ ����
$root{'right'} = getNode(2,'right');
$root{'data'}='root';
my $rootref = \%root; # �������� ������ �� ������
print "Tree was build\n";
walk($rootref); # ����� ������
