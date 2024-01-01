<mxfile host="app.diagrams.net" modified="2023-12-31T16:16:24.144Z" agent="Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36" etag="BLAfvwT42g3JROJZNJQ0" version="22.1.15" type="device">
  <diagram name="Page-1" id="AxLVSAYq3zboFlNSM6wu">
    <mxGraphModel dx="2191" dy="793" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="827" pageHeight="1169" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />
        <mxCell id="x3QHFxx95bnm12phx4dT-1" value="User SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="60" y="65" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-2" value="User SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="70" y="75" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-3" value="User SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="80" y="85" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-13" value="LB" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-100" y="55" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-14" value="LB" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-90" y="65" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-15" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-80" y="75" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-50" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="259.5" y="320" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-51" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="269.5" y="330" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-52" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="279.5" y="340" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-58" value="SNS (Driver Topic)" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="699.5" y="350" width="110.5" height="65" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-61" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="449.5" y="340" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-119" value="Driver Onboarding SVC" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="544" y="855" width="140" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-120" value="Web (ASG, Cloudwatch Alarm on CPU &amp;gt; 70%, &amp;lt;30%)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="539.5" y="775" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-121" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="239.5" y="775" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-122" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="249.5" y="785" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-123" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="259.5" y="795" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-124" value="Worker (Lambda)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="549.5" y="955" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-125" value="SQS (Driver Onboarding Queue)" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="210.5" y="965" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-126" value="Worker (Lambda)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="559.5" y="965" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-127" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.75;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-128" target="x3QHFxx95bnm12phx4dT-129" edge="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-128" value="Worker (Lambda)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="569.5" y="975" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-129" value="SNS (Driver Onboaring Topic)" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="909.5" y="960" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-130" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=-0.008;entryY=0.65;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-125" target="x3QHFxx95bnm12phx4dT-124" edge="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-131" value="Web (ASG, Cloudwatch Alarm on CPU &amp;gt; 70%, &amp;lt;30%)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="549.5" y="785" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-132" value="Web (ASG, Cloudwatch Alarm on CPU &amp;gt; 70%, &amp;lt;30%)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="559.5" y="795" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-133" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0.008;entryY=0.867;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-123" target="x3QHFxx95bnm12phx4dT-120" edge="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-134" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;exitX=0.379;exitY=1.017;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-125" target="x3QHFxx95bnm12phx4dT-129" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="259.55999999999995" y="1043" as="sourcePoint" />
            <mxPoint x="969" y="1035" as="targetPoint" />
            <Array as="points">
              <mxPoint x="259.5" y="1135" />
              <mxPoint x="599.5" y="1135" />
              <mxPoint x="969.5" y="1135" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-135" value="SQS (Driver Onboarding DLQ)" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="211" y="1140" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-136" value="" style="endArrow=classic;html=1;rounded=0;exitX=0;exitY=1;exitDx=0;exitDy=0;entryX=1;entryY=0.5;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-126" target="x3QHFxx95bnm12phx4dT-135" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="660" y="1160" as="sourcePoint" />
            <mxPoint x="710" y="1110" as="targetPoint" />
            <Array as="points">
              <mxPoint x="560" y="1170" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-137" value="Failed Evetns" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-136" vertex="1" connectable="0">
          <mxGeometry x="-0.4892" y="2" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-138" value="" style="endArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" target="x3QHFxx95bnm12phx4dT-125" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="210" y="1170" as="sourcePoint" />
            <mxPoint x="710" y="1110" as="targetPoint" />
            <Array as="points">
              <mxPoint x="140" y="1170" />
              <mxPoint x="140" y="995" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-139" value="Redrive" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-138" vertex="1" connectable="0">
          <mxGeometry x="0.0016" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-140" value="RDS Proxy (Connection Pool)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="940" y="835" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-141" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-132" target="x3QHFxx95bnm12phx4dT-140" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="660" y="980" as="sourcePoint" />
            <mxPoint x="710" y="930" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-142" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.75;entryDx=0;entryDy=0;exitX=1;exitY=0.25;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-128" target="x3QHFxx95bnm12phx4dT-140" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="660" y="980" as="sourcePoint" />
            <mxPoint x="710" y="930" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-143" value="Availability zone" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1161.5" y="945" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-144" value="" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1150" y="770" width="140" height="200" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-145" value="Availability zone 1" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1155.5" y="765" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-146" value="RDS (Postgres)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1155.5" y="845" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-147" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1220" y="795" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-148" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1220" y="880" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-149" value="Availability zone" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1331.5" y="800" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-150" value="" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1320" y="625" width="140" height="200" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-151" value="Availability zone 2" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1325.5" y="620" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-152" value="RDS (Postgres)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1325.5" y="680" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-153" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1410" y="650" width="40" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-154" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1390" y="735" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-155" value="Availability zone" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1331.5" y="1035" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-156" value="" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1320" y="860" width="140" height="200" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-157" value="Availability zone 3" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1325.5" y="855" width="109" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-158" value="RDS (Postgres)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1325.5" y="915" width="60" height="100" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-159" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1410" y="885" width="40" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-160" value="Read Replica" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="1390" y="970" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-161" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.007;entryY=0.47;entryDx=0;entryDy=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-140" target="x3QHFxx95bnm12phx4dT-144" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="740" y="1060" as="sourcePoint" />
            <mxPoint x="790" y="1010" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-162" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.029;entryY=0.45;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0.342;exitY=-0.012;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-146" target="x3QHFxx95bnm12phx4dT-150" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="980" y="980" as="sourcePoint" />
            <mxPoint x="1030" y="930" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-163" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0;exitY=0;exitDx=0;exitDy=67.5;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-158" target="x3QHFxx95bnm12phx4dT-146" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="1301" y="787" as="sourcePoint" />
            <mxPoint x="1326" y="725" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-164" value="Driver Onboarding SVC" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="544.5" y="920" width="140" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-166" value="API Gateway" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-270" y="570" width="120" height="160" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-167" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.156;entryY=0.688;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-148" y="570" as="sourcePoint" />
            <mxPoint x="273.98" y="375.03999999999996" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-171" value="1 SignUp Driver" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-167" vertex="1" connectable="0">
          <mxGeometry x="0.0005" y="3" relative="1" as="geometry">
            <mxPoint x="-2" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-168" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=0.642;exitY=1.017;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-166" target="x3QHFxx95bnm12phx4dT-121" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="200" y="620" as="sourcePoint" />
            <mxPoint x="250" y="570" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-170" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.042;entryY=0.617;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0.863;exitY=0.313;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-15" target="x3QHFxx95bnm12phx4dT-1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-230" y="215" as="sourcePoint" />
            <mxPoint x="-180" y="165" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-172" value="DRIVER" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;" parent="1" vertex="1">
          <mxGeometry x="-710" y="670" width="30" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-175" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.033;entryY=0.438;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-166" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-600" y="710" as="sourcePoint" />
            <mxPoint x="-350" y="708" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-177" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" target="x3QHFxx95bnm12phx4dT-166" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-350" y="700" as="sourcePoint" />
            <mxPoint x="110" y="510" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-179" value="AWS Lambda Authorizer (Google OAuth2)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-277.5" y="370" width="135" height="140" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-181" value="" style="endArrow=classic;html=1;rounded=0;exitX=0.25;exitY=0;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-166" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="380" y="520" as="sourcePoint" />
            <mxPoint x="-240" y="510" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-182" value="Google OAuth2 (Authetication Server)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-420" y="165" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-183" value="" style="endArrow=classic;html=1;rounded=0;entryX=0.15;entryY=0.983;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0;exitY=0;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-179" target="x3QHFxx95bnm12phx4dT-182" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-1226" y="430" as="sourcePoint" />
            <mxPoint x="430" y="470" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-184" value="" style="endArrow=classic;html=1;rounded=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;exitX=0.383;exitY=1;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-182" target="x3QHFxx95bnm12phx4dT-179" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-770" y="520" as="sourcePoint" />
            <mxPoint x="-195" y="440" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-185" value="" style="endArrow=classic;html=1;rounded=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;exitX=0.5;exitY=1;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-179" target="x3QHFxx95bnm12phx4dT-166" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-210" y="520" as="sourcePoint" />
            <mxPoint x="-160" y="470" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-227" value="userID" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-185" vertex="1" connectable="0">
          <mxGeometry x="0.0667" y="4" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-187" value="VPC Link" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-100" y="510" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-188" value="VPC Link" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-120" y="730" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-190" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=1;entryY=0.5;entryDx=0;entryDy=0;exitX=0;exitY=0.25;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-125" target="x3QHFxx95bnm12phx4dT-58" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="50" y="970" as="sourcePoint" />
            <mxPoint x="1340" y="570" as="targetPoint" />
            <Array as="points">
              <mxPoint x="50" y="980" />
              <mxPoint x="50" y="1260" />
              <mxPoint x="1590" y="1260" />
              <mxPoint x="1590" y="540" />
              <mxPoint x="1590" y="380" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-192" value="DriverCreatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="78.5" y="960" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-193" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="540" y="660" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-194" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="550" y="670" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-195" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="560" y="680" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-196" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="600" y="1040" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-197" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="610" y="1050" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-198" value="FluentBit (Sidcar/DeamonSet)" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="620" y="1060" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-199" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.071;entryY=1.033;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0;exitY=0.75;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-196" target="x3QHFxx95bnm12phx4dT-128" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="690" y="1100" as="sourcePoint" />
            <mxPoint x="740" y="1050" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-200" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;exitX=0.671;exitY=0.017;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-120" target="x3QHFxx95bnm12phx4dT-195" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="690" y="1100" as="sourcePoint" />
            <mxPoint x="740" y="1050" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-201" value="CloudWatch Logs" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="720" y="650" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-202" value="OpenSearch" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="900" y="650" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-203" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-201" target="x3QHFxx95bnm12phx4dT-202" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="890" y="750" as="sourcePoint" />
            <mxPoint x="940" y="700" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-205" value="" style="endArrow=classic;html=1;rounded=0;exitX=0.75;exitY=0;exitDx=0;exitDy=0;entryX=0;entryY=0.25;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-193" target="x3QHFxx95bnm12phx4dT-201" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="890" y="750" as="sourcePoint" />
            <mxPoint x="940" y="700" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-206" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-179" target="x3QHFxx95bnm12phx4dT-13" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="150" y="230" as="sourcePoint" />
            <mxPoint x="310" y="-80" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-228" value="getOrCreateUserIdByEmail" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-206" vertex="1" connectable="0">
          <mxGeometry x="-0.0055" y="2" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-207" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="459.5" y="350" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-208" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="469.5" y="360" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-209" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-52" target="x3QHFxx95bnm12phx4dT-61" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="350" y="545" as="sourcePoint" />
            <mxPoint x="400" y="495" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-210" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-208" target="x3QHFxx95bnm12phx4dT-58" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="970" y="505" as="sourcePoint" />
            <mxPoint x="1020" y="455" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-211" value="Sentry" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="984" y="495" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-212" value="" style="endArrow=classic;html=1;rounded=0;" parent="1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="690" y="980" as="sourcePoint" />
            <mxPoint x="1090" y="560" as="targetPoint" />
            <Array as="points">
              <mxPoint x="720" y="780" />
              <mxPoint x="1090" y="780" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-216" value="Slack" style="ellipse;shape=cloud;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1180" y="480" width="120" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-217" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0.108;entryY=0.563;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-211" target="x3QHFxx95bnm12phx4dT-216" edge="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-220" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-218" target="x3QHFxx95bnm12phx4dT-219" edge="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-218" value="SQS (Notification Queue)" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="950" y="1460" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-219" value="Notification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1180" y="1460" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-221" value="Notification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1190" y="1470" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-222" value="Notification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="1200" y="1480" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-225" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.821;entryY=0.967;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-218" target="x3QHFxx95bnm12phx4dT-129" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="1030" y="1370" as="sourcePoint" />
            <mxPoint x="980" y="1110" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-226" value="CircuitBreaker" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-222.5" y="370" width="80" height="50" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-230" value="" style="endArrow=classic;html=1;rounded=0;entryX=0.883;entryY=0.967;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-211" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="660" y="780" as="sourcePoint" />
            <mxPoint x="1090" y="570" as="targetPoint" />
            <Array as="points">
              <mxPoint x="780" y="780" />
              <mxPoint x="1090" y="780" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-215" value="Exceptions" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="1060" y="595" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-231" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="39" y="1670" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-232" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="49" y="1680" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-233" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="59" y="1690" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-235" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="229" y="1690" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-236" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="239" y="1700" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-237" value="Blob SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="249" y="1710" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-238" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-233" target="x3QHFxx95bnm12phx4dT-235" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="129.5" y="1895" as="sourcePoint" />
            <mxPoint x="179.5" y="1845" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-239" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-237" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="749.5" y="1855" as="sourcePoint" />
            <mxPoint x="479" y="1732.5" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-240" value="DynamoDB (Key-Value/Disk based)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="479.5" y="1685" width="80.5" height="90" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-241" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.092;entryY=1.038;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="67.3543792402238" y="1482.1456207597762" as="sourcePoint" />
            <mxPoint x="-257.96000000000004" y="736.0799999999999" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-242" value="getDocumentUploadUrl" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-241" vertex="1" connectable="0">
          <mxGeometry x="0.9289" y="-1" relative="1" as="geometry">
            <mxPoint x="35" y="38" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-243" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="38.5" y="1450" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-244" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="48.5" y="1460" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-245" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="58.5" y="1470" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-246" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="228.5" y="1470" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-247" value="Driver SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="238.5" y="1480" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-248" value="Document SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="248.5" y="1490" width="131.5" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-249" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-245" target="x3QHFxx95bnm12phx4dT-246" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="129" y="1675" as="sourcePoint" />
            <mxPoint x="179" y="1625" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-252" value="PostgreSQL" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="480" y="1530" width="60" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-253" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-232" target="x3QHFxx95bnm12phx4dT-256" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="230" y="1480" as="sourcePoint" />
            <mxPoint x="280" y="1430" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-254" value="getpreSignerUploadURLAndBlobID" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-253" vertex="1" connectable="0">
          <mxGeometry x="0.068" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-255" value="S3" style="ellipse;shape=cloud;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="-740" y="400" width="120" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-256" value="CB" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="248.5" y="1545" width="36" height="25" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-257" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;" parent="1" source="x3QHFxx95bnm12phx4dT-255" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-520" y="900" as="sourcePoint" />
            <mxPoint x="-690" y="660" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-258" value="PUT preSignedUploadUrl" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" parent="x3QHFxx95bnm12phx4dT-257" vertex="1" connectable="0">
          <mxGeometry x="0.1311" y="-2" relative="1" as="geometry">
            <mxPoint x="-1" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-259" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.75;exitDx=0;exitDy=0;entryX=0;entryY=1;entryDx=0;entryDy=-15;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-248" target="x3QHFxx95bnm12phx4dT-252" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="670" y="1540" as="sourcePoint" />
            <mxPoint x="720" y="1490" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-261" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="29" y="1870" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-262" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="39" y="1880" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-263" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="49" y="1890" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-264" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="229" y="1920" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-267" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-263" target="x3QHFxx95bnm12phx4dT-264" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="129.5" y="2125" as="sourcePoint" />
            <mxPoint x="179.5" y="2075" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-268" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0;entryDx=0;entryDy=60;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-269" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="369.48" y="1964" as="sourcePoint" />
            <mxPoint x="479" y="1962.5" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-269" value="AWS DynamoDB (PK - userId, SK - clientRefId)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="419.75" y="1840" width="80.5" height="90" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-270" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="239" y="1930" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-271" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="249" y="1940" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-272" value="SNS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="554" y="2010" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-273" value="SQS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="29" y="2010" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-276" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.383;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=1;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-273" target="x3QHFxx95bnm12phx4dT-270" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="200" y="1960" as="sourcePoint" />
            <mxPoint x="250" y="1910" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-277" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;exitX=0.5;exitY=1;exitDx=0;exitDy=0;" parent="1" source="NFJOzEZMsUfpYNyDSEcR-10" target="x3QHFxx95bnm12phx4dT-272" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="200" y="1960" as="sourcePoint" />
            <mxPoint x="250" y="1910" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-281" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="218" y="2200" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-283" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0;entryDx=0;entryDy=60;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-284" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="358.48" y="2244" as="sourcePoint" />
            <mxPoint x="468" y="2242.5" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-284" value="PostgreSQL" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="458.5" y="2140" width="80.5" height="90" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-285" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="228" y="2210" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-286" value="Product Order Worker" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="238" y="2220" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-287" value="SNS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="510" y="2290" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-288" value="SQS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="-160" y="2250" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-289" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.383;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=1;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-288" target="x3QHFxx95bnm12phx4dT-285" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="189" y="2240" as="sourcePoint" />
            <mxPoint x="239" y="2190" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-290" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.05;entryY=0.3;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-286" target="x3QHFxx95bnm12phx4dT-287" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="189" y="2240" as="sourcePoint" />
            <mxPoint x="239" y="2190" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-292" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="3" y="2480" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-293" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="13" y="2490" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-294" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="23" y="2500" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-295" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="203" y="2530" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-296" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-294" target="x3QHFxx95bnm12phx4dT-295" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="103.5" y="2735" as="sourcePoint" />
            <mxPoint x="153.5" y="2685" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-297" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0;entryDx=0;entryDy=60;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-298" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="343.48" y="2574" as="sourcePoint" />
            <mxPoint x="453" y="2572.5" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-298" value="PostgreSQL" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="443.5" y="2470" width="80.5" height="90" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-299" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="213" y="2540" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-300" value="Order SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="223" y="2550" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-301" value="SNS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="490" y="2620" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-302" value="SQS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="3" y="2620" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-303" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.383;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=1;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-302" target="x3QHFxx95bnm12phx4dT-299" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="174" y="2570" as="sourcePoint" />
            <mxPoint x="224" y="2520" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-304" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.05;entryY=0.3;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-300" target="x3QHFxx95bnm12phx4dT-301" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="174" y="2570" as="sourcePoint" />
            <mxPoint x="224" y="2520" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-305" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="3" y="2780" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-306" value="LB (ALB)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="13" y="2790" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-307" value="LB (ALB, Service Discovery)" style="rhombus;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="23" y="2800" width="80" height="80" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-308" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="203" y="2830" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-309" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=0.869;exitY=0.35;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-307" target="x3QHFxx95bnm12phx4dT-308" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="103.5" y="3035" as="sourcePoint" />
            <mxPoint x="153.5" y="2985" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-310" value="" style="endArrow=classic;html=1;rounded=0;exitX=1.004;exitY=0.4;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=0;entryDx=0;entryDy=60;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-311" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="343.48" y="2874" as="sourcePoint" />
            <mxPoint x="453" y="2872.5" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-311" value="AWS DynamoDB (PK - OrderId,&amp;nbsp; SK - &lt;span style=&quot;white-space: pre;&quot;&gt;&#x9;&lt;/span&gt;uniqueRefId)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;" parent="1" vertex="1">
          <mxGeometry x="443.5" y="2770" width="80.5" height="90" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-312" value="Backgroung Verification SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="213" y="2840" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-313" value="Shipment SVC" style="rounded=0;whiteSpace=wrap;html=1;" parent="1" vertex="1">
          <mxGeometry x="223" y="2850" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-314" value="SNS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="469.5" y="3090" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-315" value="SQS" style="shape=process;whiteSpace=wrap;html=1;backgroundOutline=1;" parent="1" vertex="1">
          <mxGeometry x="3" y="2920" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-316" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.383;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0;entryY=1;entryDx=0;entryDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-315" target="x3QHFxx95bnm12phx4dT-312" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="174" y="2870" as="sourcePoint" />
            <mxPoint x="224" y="2820" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-319" value="CreateOrderCommand" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="389.5" y="2290" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-321" value="DriverOnboardStatusUpdatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="80" y="2280" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-323" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=-0.025;entryY=0.883;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" target="x3QHFxx95bnm12phx4dT-287" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint y="2660" as="sourcePoint" />
            <mxPoint x="240" y="2230" as="targetPoint" />
            <Array as="points">
              <mxPoint x="-80" y="2660" />
              <mxPoint x="-80" y="2343" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-324" value="CreateOrderCommand" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="169" y="2620" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-325" value="OrderCreatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="389.5" y="2630" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-327" value="" style="endArrow=classic;html=1;rounded=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0.433;entryY=1.033;entryDx=0;entryDy=0;entryPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-301" target="x3QHFxx95bnm12phx4dT-315" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="100" y="2710" as="sourcePoint" />
            <mxPoint x="90" y="2990" as="targetPoint" />
            <Array as="points">
              <mxPoint x="610" y="3240" />
              <mxPoint x="340" y="3240" />
              <mxPoint x="55" y="3240" />
              <mxPoint x="55" y="3110" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-328" value="OrderCreatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-31" y="3010" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-329" value="ShipmentStatusUpdatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="430" y="3020" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-331" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;exitX=0.871;exitY=-0.067;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-314" target="x3QHFxx95bnm12phx4dT-302" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="160" y="2760" as="sourcePoint" />
            <mxPoint x="210" y="2710" as="targetPoint" />
            <Array as="points">
              <mxPoint x="574" y="2730" />
              <mxPoint x="63" y="2730" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-333" value="ShipmentStatusUpdatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-69" y="2700" width="118" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-336" value="ADMIN" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;" parent="1" vertex="1">
          <mxGeometry x="-710" y="885" width="30" height="60" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-339" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;" parent="1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-660" y="920" as="sourcePoint" />
            <mxPoint x="-350" y="700" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-340" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=0.088;exitY=0.375;exitDx=0;exitDy=0;exitPerimeter=0;" parent="1" source="x3QHFxx95bnm12phx4dT-261" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="-260" y="1940" as="sourcePoint" />
            <mxPoint x="-280" y="730" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-342" value="&lt;br&gt;ADMIN&lt;br&gt;&lt;br&gt;&amp;nbsp;/updateStatus" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-230" y="1110" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-344" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;" parent="1" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint y="2820" as="sourcePoint" />
            <mxPoint x="-280" y="730" as="targetPoint" />
            <Array as="points">
              <mxPoint x="-310" y="2820" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-345" value="ADMIN&lt;br&gt;&lt;br&gt;/updateStatus" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-380" y="2620" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-346" value="USER&lt;br&gt;&lt;br&gt;/markAvailableForRide" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="-390" y="2740" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-347" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;" parent="1" target="x3QHFxx95bnm12phx4dT-125" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="610" y="2650" as="sourcePoint" />
            <mxPoint x="270" y="930" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1170" y="2650" />
              <mxPoint x="1170" y="1830" />
              <mxPoint x="1170" y="1610" />
              <mxPoint x="880" y="1610" />
              <mxPoint x="880" y="1320" />
              <mxPoint x="20" y="1320" />
              <mxPoint x="20" y="930" />
              <mxPoint x="271" y="930" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-348" value="OrderStatusUpdatedEvent" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="79" y="905" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-349" value="BackgoundVerificationStatusUpdated" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="554" y="1955" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-350" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;" parent="1" source="x3QHFxx95bnm12phx4dT-272" target="x3QHFxx95bnm12phx4dT-351" edge="1">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="730" y="1890" as="sourcePoint" />
            <mxPoint x="310" y="960" as="targetPoint" />
            <Array as="points">
              <mxPoint x="830" y="2030" />
              <mxPoint x="830" y="1330" />
              <mxPoint x="670" y="1330" />
              <mxPoint x="10" y="1330" />
              <mxPoint y="900" />
              <mxPoint x="310" y="900" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-351" value="Text" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="279" y="965" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-352" value="Text" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="230" y="1240" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-353" value="Text" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="230" y="1240" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="x3QHFxx95bnm12phx4dT-354" value="BackgrondVerificationStatusUpdated" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" parent="1" vertex="1">
          <mxGeometry x="49" y="875" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-1" value="CreateOrderCommand" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="-180" y="2630" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-3" value="" style="endArrow=classic;html=1;rounded=0;exitX=0.776;exitY=1.011;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0.692;entryY=-0.05;entryDx=0;entryDy=0;entryPerimeter=0;" edge="1" parent="1" source="NFJOzEZMsUfpYNyDSEcR-5" target="NFJOzEZMsUfpYNyDSEcR-4">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="240" y="2940" as="sourcePoint" />
            <mxPoint x="506" y="2920" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-4" value="AWS Lambda" style="rounded=0;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="419" y="2940" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-6" value="" style="endArrow=classic;html=1;rounded=0;exitX=0.776;exitY=1.011;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0.692;entryY=-0.05;entryDx=0;entryDy=0;entryPerimeter=0;" edge="1" parent="1" source="x3QHFxx95bnm12phx4dT-311" target="NFJOzEZMsUfpYNyDSEcR-5">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="506" y="2861" as="sourcePoint" />
            <mxPoint x="502" y="2937" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-5" value="DynamoDB stream" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="458.5" y="2890" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-7" value="" style="endArrow=classic;html=1;rounded=0;exitX=0.358;exitY=1.05;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;" edge="1" parent="1" source="NFJOzEZMsUfpYNyDSEcR-4" target="x3QHFxx95bnm12phx4dT-314">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="240" y="2940" as="sourcePoint" />
            <mxPoint x="290" y="2890" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-8" value="" style="endArrow=classic;startArrow=classic;html=1;rounded=0;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.25;entryY=1;entryDx=0;entryDy=0;" edge="1" parent="1" source="x3QHFxx95bnm12phx4dT-288" target="x3QHFxx95bnm12phx4dT-272">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="240" y="2180" as="sourcePoint" />
            <mxPoint x="290" y="2130" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-10" value="AWS Lambda" style="whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="650" y="1855" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-12" value="DynamoDB Stream" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="540" y="1870" width="60" height="30" as="geometry" />
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-14" value="" style="endArrow=classic;html=1;rounded=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;exitPerimeter=0;" edge="1" parent="1" source="x3QHFxx95bnm12phx4dT-269" target="NFJOzEZMsUfpYNyDSEcR-12">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="240" y="2000" as="sourcePoint" />
            <mxPoint x="290" y="1950" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="NFJOzEZMsUfpYNyDSEcR-15" value="" style="endArrow=classic;html=1;rounded=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;exitX=1;exitY=0.5;exitDx=0;exitDy=0;" edge="1" parent="1" source="NFJOzEZMsUfpYNyDSEcR-12" target="NFJOzEZMsUfpYNyDSEcR-10">
          <mxGeometry width="50" height="50" relative="1" as="geometry">
            <mxPoint x="240" y="2000" as="sourcePoint" />
            <mxPoint x="290" y="1950" as="targetPoint" />
          </mxGeometry>
        </mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
